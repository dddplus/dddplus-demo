package org.example.oms.d.stock.domain.service;

import io.github.dddplus.annotation.DomainService;
import lombok.extern.slf4j.Slf4j;
import org.example.cp.oms.spec.model.IOrderMain;
import org.example.cp.oms.spec.model.vo.IOrderItem;
import org.example.oms.d.stock.domain.facade.rpc.IRemoteStockRpc;
import org.example.oms.d.stock.spec.StockDomain;
import org.example.oms.d.stock.spec.service.IStockService;

import javax.annotation.Resource;

@DomainService(domain = StockDomain.CODE)
@Slf4j
public class StockService implements IStockService {

    @Resource
    private IRemoteStockRpc remoteStockRpc;

    @Override
    public void occupyStock(IOrderMain orderMain) {
        log.info("会通过infrastructure层调用库存中心的RPC接口，执行预占库存动作");
        log.info("这里的逻辑，主要是根据不同业务场景组织库存中心RPC的入参，并对返回结果进行处理");

        for (IOrderItem item : orderMain.itemDelegate().getItems()) {
            // 生产环境，看到会批量调用，这里只是演示
            remoteStockRpc.doOccupy(item.getSku(), item.getQuantity());
        }
    }
}
