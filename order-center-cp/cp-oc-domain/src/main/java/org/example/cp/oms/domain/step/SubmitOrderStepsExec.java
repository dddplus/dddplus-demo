package org.example.cp.oms.domain.step;

import io.github.dddplus.runtime.StepsExecTemplate;
import lombok.extern.slf4j.Slf4j;
import org.example.cp.oms.domain.model.OrderMain;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SubmitOrderStepsExec extends StepsExecTemplate<SubmitOrderStep, OrderMain> {

    @Override
    protected void beforeStep(SubmitOrderStep step, OrderMain model) {
        log.info("step:{}.{} before:{}", step.activityCode(), step.stepCode(), model.label());
    }

    @Override
    protected void afterStep(SubmitOrderStep step, OrderMain model) {
    }
}
