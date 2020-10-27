package org.example.oms.d.stock.domain.facade.rpc;

public interface IRemoteStockRpc {

    void doOccupy(String sku, Integer quantity);
}
