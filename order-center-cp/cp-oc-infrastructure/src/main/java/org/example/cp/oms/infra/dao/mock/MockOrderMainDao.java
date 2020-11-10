package org.example.cp.oms.infra.dao.mock;

import org.example.cp.oms.infra.dao.OrderMainDao;
import org.example.cp.oms.infra.po.OrderMainData;
import org.springframework.stereotype.Component;

// 实际项目，可以使用MyBatis/Hibernate/JPA等
@Component
public class MockOrderMainDao implements OrderMainDao {
    @Override
    public void insert(OrderMainData orderMainData) {

    }

    @Override
    public OrderMainData getById(Long id) {
        return null;
    }
}
