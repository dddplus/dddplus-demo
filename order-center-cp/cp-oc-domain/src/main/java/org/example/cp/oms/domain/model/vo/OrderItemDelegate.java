package org.example.cp.oms.domain.model.vo;

import org.example.cp.oms.domain.model.OrderModelCreator;
import org.example.cp.oms.spec.model.vo.IOrderItem;
import org.example.cp.oms.spec.model.vo.IOrderItemDelegate;
import org.example.cp.oms.spec.model.vo.IProduct;
import org.example.cp.oms.spec.model.vo.IProductDelegate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDelegate implements IOrderItemDelegate {

    private List<OrderItem> items;

    private OrderItemDelegate() {}

    public static OrderItemDelegate createWith(@NotNull OrderModelCreator creator) {
        OrderItemDelegate delegate = new OrderItemDelegate();
        delegate.items = new ArrayList<>();
        return delegate;
    }

    @Override
    public List<? extends IOrderItem> getItems() {
        return items;
    }
}
