package org.example.bp.oms.fresh.extension;

import io.github.dddplus.annotation.Extension;
import io.github.dddplus.ext.IDecideStepsExt;
import io.github.dddplus.model.IDomainModel;
import lombok.extern.slf4j.Slf4j;
import org.example.bp.oms.fresh.FreshPartner;
import org.example.cp.oms.spec.Steps;

import javax.validation.constraints.NotNull;
import java.util.*;

@Extension(code = FreshPartner.CODE, name = "生鲜业务前台对所有流程的编排", value = "freshDecideStepsExt")
@Slf4j
public class DecideStepsExt implements IDecideStepsExt {
    private static final List<String> emptySteps = Collections.emptyList();

    @Override
    public List<String> decideSteps(@NotNull IDomainModel model, @NotNull String activityCode) {
        List<String> steps = stepsRegistry.get(activityCode);
        if (steps == null) {
            return emptySteps;
        }

        log.info("Fresh steps: {}", steps);
        return steps;
    }

    // 所有流程步骤注册表 {activityCode, stepCodeList}
    private static Map<String, List<String>> stepsRegistry = new HashMap<>();
    static {
        // 接单的步骤
        List<String> submitOrderSteps = new ArrayList<>();
        stepsRegistry.put(Steps.SubmitOrder.Activity, submitOrderSteps);
        submitOrderSteps.add(Steps.SubmitOrder.BasicStep);
        submitOrderSteps.add(Steps.SubmitOrder.StockStep);
        submitOrderSteps.add(Steps.SubmitOrder.PersistStep);

        // 订单取消步骤
        List<String> cancelOrderSteps = new ArrayList<>();
        stepsRegistry.put(Steps.CancelOrder.Activity, cancelOrderSteps);
        cancelOrderSteps.add(Steps.CancelOrder.StateStep);
    }
}
