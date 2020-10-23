package org.example.cp.oms.domain.facade.mq;

import org.example.cp.oms.domain.model.OrderMain;

import javax.validation.constraints.NotNull;

public interface IMessageProducer {

    void produce(@NotNull OrderMain orderModel);
}
