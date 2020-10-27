package org.example.cp.oms.domain.step.submitorder;

import io.github.dddplus.annotation.Step;
import org.example.cp.oms.domain.model.OrderMain;
import org.example.cp.oms.domain.step.SubmitOrderStep;
import org.example.cp.oms.spec.Steps;
import org.example.cp.oms.spec.exception.OrderException;
import org.example.oms.d.stock.spec.service.IStockService;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

@Step(value = "stockStep")
public class StockStep extends SubmitOrderStep {

    // 演示：通过库存支撑域来为订单核心域提供方便的服务
    @Resource
    private IStockService stockService;
    
    @Override
    public void execute(@NotNull OrderMain model) throws OrderException {
        stockService.occupyStock("SKU098");
    }

    @Override
    public String stepCode() {
        return Steps.SubmitOrder.StockStep;
    }
}
