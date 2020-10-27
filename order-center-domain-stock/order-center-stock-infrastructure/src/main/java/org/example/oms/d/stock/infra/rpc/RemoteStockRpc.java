package org.example.oms.d.stock.infra.rpc;

import lombok.extern.slf4j.Slf4j;
import org.example.oms.d.stock.domain.facade.rpc.IRemoteStockRpc;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RemoteStockRpc implements IRemoteStockRpc {

    @Override
    public void doOccupy(String sku, Integer quantity) {
        log.info("RPC调用库存中心系统，返回值是OK!");
    }
}
