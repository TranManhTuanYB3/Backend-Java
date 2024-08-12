package vn.eledevo.vksbe.mapper;

import org.mapstruct.Mapper;
import vn.eledevo.vksbe.dto.request.OrderRequest;
import vn.eledevo.vksbe.dto.response.OrderResponse;
import vn.eledevo.vksbe.entity.Order;

@Mapper(componentModel = "spring")
public abstract class OrderMapper extends BaseMapper<OrderRequest, OrderResponse, Order>{
}
