package org.example.cp.oms.domain.step.submitorder;

import io.github.dddplus.annotation.Step;
import org.example.cp.oms.domain.model.OrderMain;
import org.example.cp.oms.domain.step.SubmitOrderStep;
import org.example.cp.oms.spec.Steps;
import org.example.cp.oms.spec.exception.OrderException;

import javax.validation.constraints.NotNull;

@Step(value = "submitPresortStep", name = "预分拣步骤")
public class PresortStep extends SubmitOrderStep {

    @Override
    public void execute(@NotNull OrderMain model) throws OrderException {
        // TODO 把预分拣扩展点从BasicStep移动到这里
    }

    @Override
    public String stepCode() {
        return Steps.SubmitOrder.PresortStep;
    }
}
