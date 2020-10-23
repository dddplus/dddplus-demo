package org.example.cp.oms.infra.translator;

import org.example.cp.oms.infra.po.OrderMainData;
import io.github.dddplus.IBaseTranslator;
import org.example.cp.oms.domain.model.OrderMain;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        typeConversionPolicy = ReportingPolicy.ERROR
)
public interface OrderTranslator extends IBaseTranslator<OrderMain, OrderMainData> {

    OrderTranslator instance = Mappers.getMapper(OrderTranslator.class);

    @Override
    OrderMainData translate(OrderMain orderModel);
}
