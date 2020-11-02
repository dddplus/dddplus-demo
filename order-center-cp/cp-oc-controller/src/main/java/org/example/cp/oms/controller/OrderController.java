package org.example.cp.oms.controller;

import lombok.extern.slf4j.Slf4j;
import io.github.dddplus.api.RequestProfile;
import io.github.dddplus.runtime.registry.Container;
import org.example.cp.oms.domain.model.OrderMain;
import org.example.cp.oms.domain.model.OrderModelCreator;
import org.example.cp.oms.domain.service.SubmitOrder;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class OrderController {

    // DDD Application Layer depends on Domain Layer
    @Resource
    private SubmitOrder submitOrder;

    // 模拟下单
    // curl -XPOST localhost:9090/order?type=isv
    @RequestMapping(value = "/order", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String submitOrder(@RequestParam(required = false) String type) {
        if (type == null) {
            type = "ISV"; // ISV by default
        }

        log.info("type={}", type);

        // DTO 转换为 domain model，通过creator保护、封装domain model
        // 具体项目使用MapStruct会更方便，这里为了演示，全手工进行对象转换了
        RequestProfile requestProfile = new RequestProfile();
        requestProfile.setTraceId(String.valueOf(System.nanoTime()));

        // 演示个性化字段：站点联系人号码是ISV前台场景才会需要的字段，其他场景不需要
        requestProfile.getExt().put("_station_contact_", "139100988343");
        MDC.put("tid", requestProfile.getTraceId()); // session scope log identifier

        // 这里手工创建一个模拟下单的请求
        OrderModelCreator creator = new OrderModelCreator();
        creator.setRequestProfile(requestProfile);
        creator.setSource(type);
        creator.setCustomerNo("goog"); // if 'home', HomeAppliancePattern will match
        creator.setExternalNo("20200987655");
        OrderMain model = OrderMain.createWith(creator);

        // 调用domain service完成该use case
        submitOrder.submit(model);
        // ISV业务前台的下单执行：
        //   SerializableIsolationExt -> DecideStepsExt -> BasicStep(PresortExt) -> PersistStep(AssignOrderNoExt, CustomModelAbility) -> BroadcastStep
        // 查看日志，了解具体执行情况
        return "Order accepted!";
    }

    // 模拟热加载Plugin Jar
    // curl localhost:9090/reload?plugin=isv
    @RequestMapping(value = "/reload")
    @ResponseBody
    public String reload(@RequestParam(required = false) String plugin) {
        MDC.put("tid", String.valueOf(System.nanoTime())); // session scope log identifier

        if (plugin == null) {
            plugin = "isv";
        } else {
            plugin = plugin.toLowerCase();
        }

        String pluginJar = plugins.get(plugin);
        if (pluginJar == null) {
            log.warn("Invalid plugin param:{}", plugin);
            return "Unknown plugin:" + plugin;
        }

        log.info("active plugins: {}", Container.getInstance().getActivePlugins());

        log.info("Reloading plugin:{} {}", plugin, pluginJar);
        boolean useSpring = true;
        if (plugin.toLowerCase().equals("fresh")) {
            useSpring = false;
        }
        try {
            // 具体使用时，需要一套plugin jar的发布平台配合使用：发布，灰度发布，回滚，版本控制，打包管理等
            Container.getInstance().loadPartnerPlugin(plugin, "v1", pluginJar, useSpring);
        } catch (Throwable cause) {
            log.error("fails to reload Plugin:{}", plugin, cause);
            return cause.getMessage();
        }

        return plugin + " Plugin Reloaded!";
    }

    private static final Map<String, String> plugins = new HashMap<>();
    static {
        plugins.put("isv", "order-center-bp-isv/target/order-center-bp-isv-0.0.1.jar");
        plugins.put("ka", "order-center-bp-ka/target/order-center-bp-ka-0.0.1.jar");
        plugins.put("fresh", "order-center-bp-fresh/target/order-center-bp-fresh-0.0.1.jar");
    }
}
