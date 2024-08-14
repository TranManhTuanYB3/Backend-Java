package vn.eledevo.vksbe.service.order;

import vn.eledevo.vksbe.dto.request.OrderRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.OrderResponse;
import vn.eledevo.vksbe.entity.Order;
import vn.eledevo.vksbe.exception.ValidationException;

import java.util.List;

public interface OrderService {
    ApiResponse<List<OrderResponse>> getAllOrder();
    ApiResponse<List<OrderResponse>> getOrder(Order textSearch, String sortField, String sortDirection, int currentPage, int limitPage);
    ApiResponse<Order> addOrder(OrderRequest orderRequest) throws ValidationException;
    ApiResponse<Order> updateOrder(Long id, OrderRequest orderRequest) throws ValidationException;
    ApiResponse<Order> deleteOrder(Long id) throws ValidationException;
}
