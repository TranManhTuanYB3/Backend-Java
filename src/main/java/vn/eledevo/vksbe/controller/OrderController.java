package vn.eledevo.vksbe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.eledevo.vksbe.dto.request.OrderRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.OrderResponse;
import vn.eledevo.vksbe.entity.Order;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.service.order.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrder(){
        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrder(
            @RequestBody Order textSearch,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "") String sortDirection,
            @RequestParam(defaultValue = "0") int currentPage,
            @RequestParam(defaultValue = "3") int limitPage
    ){
        return ResponseEntity.ok(orderService.getOrder(textSearch, sortField, sortDirection, currentPage, limitPage));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Order>> addOrder(@RequestBody OrderRequest orderRequest) throws ValidationException {
        return ResponseEntity.ok(orderService.addOrder(orderRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Order>> updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest) throws ValidationException {
        return ResponseEntity.ok(orderService.updateOrder(id, orderRequest));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Order>> deleteOrder(@PathVariable Long id) throws ValidationException {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }
}
