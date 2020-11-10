package org.example.cp.oms.infra.dao;

import org.example.cp.oms.infra.po.OrderItemData;

import java.util.List;

public interface OrderItemDao {

    List<OrderItemData> itemsOfOrder(Long orderId);
}
