package org.example.cp.oms.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import io.github.dddplus.api.RequestProfile;
import org.example.cp.oms.domain.model.vo.OrderItemDelegate;
import org.example.cp.oms.spec.exception.OrderException;
import org.example.cp.oms.domain.model.vo.ProductDelegate;
import org.example.cp.oms.spec.model.IOrderMain;
import org.example.cp.oms.spec.model.vo.IProductDelegate;

import javax.validation.constraints.NotNull;

/**
 * 订单主档.
 *
 * <p>注意，它没有实现Serializable，因为它不会网络传递，也不会本地文件存储.</p>
 */
@Getter // 注意：它没有@Setter，是为了封装，包含订单一致性
@Slf4j
public class OrderMain implements IOrderMain {

    private String source;
    private String customerNo;

    private String orderNo;

    private String externalNo;

    private RequestProfile requestProfile;

    @Setter
    private String activity;

    @Setter
    private String step;

    private ProductDelegate productDelegate;
    private OrderItemDelegate orderItemDelegate;

    @Getter
    private String x1, x2;

    public static OrderMain createWith(@NotNull OrderModelCreator creator) throws OrderException {
        log.debug("creating with {}", creator);
        return new OrderMain(creator).validate();
    }

    private OrderMain(OrderModelCreator creator) {
        this.source = creator.getSource();
        this.customerNo = creator.getCustomerNo();
        this.externalNo = creator.getExternalNo();
        this.requestProfile = creator.getRequestProfile();

        this.productDelegate = ProductDelegate.createWith(creator);
        this.orderItemDelegate = OrderItemDelegate.createWith(creator);
    }

    private OrderMain validate() throws OrderException {
        // 模型本身的基础校验
        return this;
    }

    @Override
    public void assignOrderNo(Object who, String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String currentStep() {
        return step;
    }

    @Override
    public String currentActivity() {
        return activity;
    }

    @Override
    public boolean isColdChain() {
        return false;
    }

    @Override
    public boolean isB2B() {
        return false;
    }

    @Override
    public void setX1(String x1) {
        this.x1 = x1;
    }

    @Override
    public void setX2(String x2) {
        this.x2 = x2;
    }

    @Override
    public IProductDelegate productDelegate() {
        return null;
    }

    @Override
    public RequestProfile requestProfile() {
        return requestProfile;
    }

    @Override
    public String customerProvidedOrderNo() {
        return externalNo;
    }

    public String label() {
        return "Order(source=" + source + ", customer=" + customerNo + ")";
    }
}
