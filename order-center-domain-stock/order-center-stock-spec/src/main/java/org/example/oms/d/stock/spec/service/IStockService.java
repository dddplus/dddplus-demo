package org.example.oms.d.stock.spec.service;

import io.github.dddplus.model.IDomainService;

// 库存相关的服务，都收敛在库存支撑域，通过领域服务提供给订单核心域调用：JVM内调用, not RPC
public interface IStockService extends IDomainService {

    /**
     * 预占预存.
     *
     * @param sku 商品的sku编号
     */
    void occupyStock(String sku);

}
