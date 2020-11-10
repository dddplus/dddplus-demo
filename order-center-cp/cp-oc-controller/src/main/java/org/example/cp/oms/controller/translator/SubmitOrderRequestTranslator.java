package org.example.cp.oms.controller.translator;

import io.github.dddplus.IBaseTranslator;
import org.example.cp.oms.controller.dto.SubmitOrderRequest;
import org.example.cp.oms.domain.model.OrderModelCreator;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubmitOrderRequestTranslator extends IBaseTranslator<SubmitOrderRequest, OrderModelCreator> {
    SubmitOrderRequestTranslator instance = Mappers.getMapper(SubmitOrderRequestTranslator.class);
}
