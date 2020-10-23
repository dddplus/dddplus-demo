package org.example.cp.oms.spec.model.vo;

/**
 * 订单包含的服务产品信息.
 *
 * <p>例如：仓内加工，货到付款，保价等，都是额外的服务产品.</p>
 */
public interface IProduct {

    /**
     * 产品标识码.
     */
    String code();
}
