package org.example.cp.oms.infra.repository;

import lombok.extern.slf4j.Slf4j;
import org.example.cp.oms.domain.facade.repository.IOrderRepository;
import org.example.cp.oms.domain.model.OrderMain;
import org.example.cp.oms.domain.model.OrderModelCreator;
import org.example.cp.oms.infra.dao.OrderItemDao;
import org.example.cp.oms.infra.dao.OrderMainDao;
import org.example.cp.oms.infra.manager.IOrderManager;
import org.example.cp.oms.infra.po.OrderItemData;
import org.example.cp.oms.infra.po.OrderMainData;
import org.example.cp.oms.infra.translator.Data2Model;
import org.example.cp.oms.infra.translator.Model2Data;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@Repository
@Slf4j
public class OrderRepository implements IOrderRepository {

    @Resource
    private IOrderManager orderManager;

    @Resource
    private OrderItemDao orderItemDao;

    @Resource
    private OrderMainDao orderMainDao;

    @Override
    public void persist(@NotNull OrderMain orderModel) {
        log.info("落库：{}", orderModel);

        if (true) {
            return;
        }

        OrderMainData orderMainData = Model2Data.instance.translate(orderModel);
        orderManager.insert(orderMainData);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderMain getOrder(@NotNull Long orderId) {
        // 数据库里拿出主档、明细档数据
        OrderMainData orderMainData = orderMainDao.getById(orderId);
        List<OrderItemData> orderItemDataList = orderItemDao.itemsOfOrder(orderId);

        // 通过MapStruct转换为creator这个契约对象，再创建领域模型
        OrderModelCreator creator = Data2Model.instance.translate(orderMainData, orderItemDataList);
        return OrderMain.createWith(creator);
    }

}
