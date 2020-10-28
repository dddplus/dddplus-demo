package org.example.cp.oms;

import io.github.dddplus.annotation.UnderDevelopment;
import io.github.dddplus.api.RequestProfile;
import io.github.dddplus.plugin.IPlugin;
import io.github.dddplus.runtime.registry.Container;
import lombok.extern.slf4j.Slf4j;
import org.example.cp.oms.domain.model.OrderMain;
import org.example.cp.oms.domain.model.OrderModelCreator;
import org.example.cp.oms.domain.service.SubmitOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static io.github.dddplus.testing.LogAssert.assertContains;
import static org.junit.Assert.assertEquals;

@Slf4j
@Ignore
public class PluginMechanismTest {
    private static final String localKaJar = "../../order-center-bp-ka/target/order-center-bp-ka-0.0.1.jar";
    private static final String localIsvJar = "../../order-center-bp-isv/target/order-center-bp-isv-0.0.1.jar";
    private static final String localFreshJar = "../../order-center-bp-fresh/target/order-center-bp-fresh-0.0.1.jar";

    @UnderDevelopment // 需要运行在 profile:plugin 下，运行前需要mvn package为Plugin打包
    @Test
    public void dynamicLoadPlugins() throws Throwable {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-test.xml");
        applicationContext.start();

        for (int i = 0; i < 5; i++) {
            // 同一个jar，load多次，模拟热更新，然后下单验证：走ISV前台逻辑
            log.info(String.join("", Collections.nCopies(50, String.valueOf(i + 1))));
            Container.getInstance().loadPartnerPlugin("isv", "v1", localIsvJar, true);
            submitOrder(applicationContext, "ISV");

            // 通过日志验证执行正确性
            assertContains(
                    // 验证 AutoLoggerAspect 被创建
                    "Spring created instance AutoLoggerAspect!", "AutoLoggerAspect 注册 Spring lifecycle ok",
                    // IsvPartner在动态加载时自动创建实例
                    "ISV new instanced",
                    // isv.PluginListener 被调用
                    "ISV Jar loaded, ",
                    // @AutoLogger
                    "DecideStepsExt.decideSteps 入参",
                    // isv.DecideStepsExt.decideSteps 调用了中台的 stockService.preOccupyStock
                    "预占库存：SKU From ISV",
                    // ISV的步骤编排
                    "steps [basic, persist, mq]",
                    // @AutoLogger
                    "org.example.bp.oms.isv.extension.PresortExt.presort 入参",
                    // isv.PresortExt
                    "ISV里预分拣的结果：1", "count(a): 2", "仓库号：WH009",
                    // 加载properties资源，并且有中文
                    "加载资源文件成功！站点名称：北京市海淀区中关村中路1号",
                    // @AutoLogger
                    "org.example.bp.oms.isv.extension.CustomModelExt.explain 入参:",
                    // CustomModel，扩展属性机制
                    "站点联系人号码：139100988343，保存到x2字段",
                    "已经发送给MQ"
            );
        }

        log.info(String.join("", Collections.nCopies(50, "=")));

        // 加载KA插件，并给KA下单
        Container.getInstance().loadPartnerPlugin("ka", "v1", localKaJar, true);
        submitOrder(applicationContext, "KA");
        assertContains(
                "KA 预占库存 GSM098",
                "KA的锁TTL大一些",
                "steps [basic, persist]"
        );

        // 目前已经加载了2个Plugin Jar
        assertEquals(2, Container.getInstance().getActivePlugins().size());
        for (IPlugin plugin : Container.getInstance().getActivePlugins().values()) {
            log.info("Plugin: {}", plugin.getCode());
        }

        log.info("sleeping 2m，等待修改bp-isv里逻辑后发布新jar...");
        TimeUnit.MINUTES.sleep(2); // 等待手工发布新jar
        log.info("2m is up, go!");
        Container.getInstance().loadPartnerPlugin("isv", "v2", localIsvJar, true);
        submitOrder(applicationContext, "ISV"); // 重新提交订单，看看是否新jar逻辑生效

        applicationContext.stop();
    }

    @Test
    public void dynamicLoadPartnerWithoutSpring() throws Throwable {
        // 加载中台容器
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-test.xml");
        applicationContext.start();

        for (int i = 0; i < 5; i++) {
            Container.getInstance().loadPartnerPlugin("fresh", "v1", localFreshJar, false);
            submitOrder(applicationContext, "Fresh");

            assertContains(
                    "Fresh里预分拣的结果：2",
                    "我，Fresh，要发个MQ通知我的下游！",
                    "RPC调用库存中心系统，返回值是OK",
                    "Fresh steps: [basic, stock, persist]"
            );
        }

        applicationContext.stop();
    }

    private void submitOrder(ApplicationContext applicationContext, String source) {
        // prepare the domain model
        RequestProfile requestProfile = new RequestProfile();
        requestProfile.getExt().put("_station_contact_", "139100988343");
        OrderModelCreator creator = new OrderModelCreator();
        creator.setRequestProfile(requestProfile);
        creator.setSource(source);
        creator.setCustomerNo("home"); // HomeAppliancePattern
        creator.setExternalNo("20200987655");
        OrderMain orderModel = OrderMain.createWith(creator);

        // call the domain service
        SubmitOrder submitOrder = (SubmitOrder) applicationContext.getBean("submitOrder");
        // Partner(ISV)的下单执行：
        //     SerializableIsolationExt -> DecideStepsExt -> BasicStep(PresortExt) -> PersistStep(AssignOrderNoExt) -> BroadcastStep
        // Partner(KA)的下单执行：
        //     SerializableIsolationExt -> DecideStepsExt -> BasicStep -> PersistStep(AssignOrderNoExt)
        submitOrder.submit(orderModel);
    }
}
