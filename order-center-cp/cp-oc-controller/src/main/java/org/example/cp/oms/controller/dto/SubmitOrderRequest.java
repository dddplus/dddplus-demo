package org.example.cp.oms.controller.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class SubmitOrderRequest implements Serializable {
    private static final long serialVersionUID = 870061998490977022L;

    @NotNull
    private String source;

    private String customerNo;
    private String externalNo;

    @Valid
    private List<OrderItem> orderItemList;

    @Data
    public static class OrderItem implements Serializable {
        private static final long serialVersionUID = 870061998490977022L;

        @NotNull
        private String sku;

        @NotNull
        private Integer quantity;

        private String orderLine;

        private BigDecimal price;
    }
}
