package org.example.bp.oms.fresh.extension;

import io.github.dddplus.annotation.Extension;
import lombok.extern.slf4j.Slf4j;
import org.example.bp.oms.fresh.FreshPartner;
import org.example.cp.oms.spec.ext.IPresortExt;
import org.example.cp.oms.spec.model.IOrderModel;

import javax.validation.constraints.NotNull;

@Extension(code = FreshPartner.CODE, value = "freshPresort")
@Slf4j
public class PresortExt implements IPresortExt {

    @Override
    public void presort(@NotNull IOrderModel model) {
        log.info("Fresh里预分拣的结果：{}", new MockInnerClass().getResult());
    }

    // 演示内部类的使用
    private static class MockInnerClass {
        int getResult() {
            return 2;
        }
    }
}
