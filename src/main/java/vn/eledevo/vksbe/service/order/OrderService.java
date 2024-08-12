package vn.eledevo.vksbe.service.order;

import vn.eledevo.vksbe.dto.request.OrderRequest;
import vn.eledevo.vksbe.dto.response.OrderResponse;
import vn.eledevo.vksbe.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAllOrder();
    List<OrderResponse> getOrder(Order textSearch, String sortField, String sortDirection, int currentPage, int limitPage);
    Order addOrder(OrderRequest orderRequest);
    Order updateOrder(Long id, OrderRequest orderRequest);
    Order deleteOrder(Long id);
}
