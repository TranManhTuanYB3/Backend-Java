package vn.eledevo.vksbe.mapper;

import org.mapstruct.Mapper;
import vn.eledevo.vksbe.dto.request.OrderRequest;
import vn.eledevo.vksbe.dto.response.OrderResponse;
import vn.eledevo.vksbe.entity.Order;

@Mapper(componentModel = "spring")
public class OrderMapper{
    public OrderResponse mapEntityToResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setEmployeeID(order.getEmployeeID());
        orderResponse.setEmployeeName(order.getEmployeeName());
        orderResponse.setCustomerName(order.getCustomerName());
        orderResponse.setCreatedAt(order.getCreatedAt());
        orderResponse.setUpdatedAt(order.getUpdatedAt());
        orderResponse.setPrice(order.getPrice());
        orderResponse.setStatus(order.getStatus());
        return orderResponse;
    }

    public Order mapRequestToEntity(OrderRequest orderRequest){
        Order order = new Order();
        order.setPrice(orderRequest.getPrice());
        order.setStatus(orderRequest.getStatus());
        return order;
    }
}
