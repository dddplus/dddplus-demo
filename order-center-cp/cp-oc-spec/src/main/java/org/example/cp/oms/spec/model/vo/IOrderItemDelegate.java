package org.example.cp.oms.spec.model.vo;

import java.util.List;

public interface IOrderItemDelegate {
    List<? extends IOrderItem> getItems();
}
