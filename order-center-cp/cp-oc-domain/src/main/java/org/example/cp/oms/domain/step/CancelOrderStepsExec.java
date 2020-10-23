package org.example.cp.oms.domain.step;

import io.github.dddplus.runtime.StepsExecTemplate;
import lombok.extern.slf4j.Slf4j;
import org.example.cp.oms.domain.model.OrderMain;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CancelOrderStepsExec extends StepsExecTemplate<CancelOrderStep, OrderMain> {

    @Override
    protected void beforeStep(CancelOrderStep step, OrderMain model) {
        log.info("step:{}.{} before:{}", step.activityCode(), step.stepCode(), model.label());
    }

    @Override
    protected void afterStep(CancelOrderStep step, OrderMain model) {
    }
}
