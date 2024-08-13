package vn.eledevo.vksbe.service.order;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.dto.request.OrderRequest;
import vn.eledevo.vksbe.dto.response.OrderResponse;
import vn.eledevo.vksbe.entity.Customer;
import vn.eledevo.vksbe.entity.Employee;
import vn.eledevo.vksbe.entity.Order;
import vn.eledevo.vksbe.mapper.OrderMapper;
import vn.eledevo.vksbe.repository.CustomerRepository;
import vn.eledevo.vksbe.repository.EmployeeRepository;
import vn.eledevo.vksbe.repository.OrderRepository;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    CustomerRepository customerRepository;

    EmployeeRepository employeeRepository;

    OrderRepository orderRepository;

    OrderMapper orderMapper;
    @Override
    public List<OrderResponse> getAllOrder() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderResponse> orderResponseList = orderMapper.toListResponse(orderList);
        return orderResponseList;
    }

    @Override
    public List<OrderResponse> getOrder(Order textSearch, String sortField, String sortDirection, int currentPage, int limitPage) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(currentPage, limitPage, sort);
        List<Order> orderList = orderRepository.searchOrders(
                textSearch.getId(),
                textSearch.getEmployeeName(),
                textSearch.getCustomerName(),
                textSearch.getCreatedAt(),
                textSearch.getUpdatedAt(),
                textSearch.getPrice(),
                textSearch.getStatus(),
                pageable
        );
        List<OrderResponse> orderResponseList = orderMapper.toListResponse(orderList);
        return orderResponseList;
    }


    @Override
    public Order addOrder(OrderRequest orderRequest) {
        Order order = orderMapper.toEntity(orderRequest);
        Employee employee = employeeRepository.findById(orderRequest.getEmployee().getId()).get();
        Customer customer = customerRepository.findById(orderRequest.getCustomer().getId()).get();
        order.setEmployeeID(employee.getEmployeeID());
        order.setEmployeeName(employee.getName());
        order.setCustomerName(customer.getName());
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(Long id, OrderRequest orderRequest) {
        Order order = orderRepository.findById(id).get();
        order.setStatus(orderRequest.getStatus());
        return orderRepository.save(order);
    }

    @Override
    public Order deleteOrder(Long id) {
        orderRepository.deleteById(id);
        return null;
    }
}
