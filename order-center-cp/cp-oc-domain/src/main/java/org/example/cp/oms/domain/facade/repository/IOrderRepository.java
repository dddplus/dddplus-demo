package org.example.cp.oms.domain.facade.repository;

import org.example.cp.oms.domain.model.OrderMain;

import javax.validation.constraints.NotNull;

public interface IOrderRepository {

    void persist(@NotNull OrderMain orderModel);
}
