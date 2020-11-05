package org.example.cp.oms.domain.ability.extension;

import io.github.dddplus.annotation.Extension;
import lombok.extern.slf4j.Slf4j;
import org.example.cp.oms.domain.specification.ProductNotEmptySpec;
import org.example.cp.oms.spec.ext.IAssignOrderNoExt;
import org.example.cp.oms.spec.model.IOrderMain;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@Extension(code = IAssignOrderNoExt.DefaultCode, value = "defaultAssignOrderNoExt")
@Slf4j
public class DefaultAssignOrderNoExt implements IAssignOrderNoExt {

    @Resource
    private ProductNotEmptySpec productNotEmptySpec;

    @Override
    public void assignOrderNo(@NotNull IOrderMain model) {
        // 演示调用业务约束的使用：把implicit business rules变成explicit
        if (!productNotEmptySpec.satisfiedBy(model)) {
            log.warn("Spec:{} not satisfied", productNotEmptySpec);
            //throw new OrderException(OrderErrorReason.SubmitOrder.ProductEmpty);
        }

    }
}
