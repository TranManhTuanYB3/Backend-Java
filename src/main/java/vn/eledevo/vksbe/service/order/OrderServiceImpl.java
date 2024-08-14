package vn.eledevo.vksbe.service.order;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.dto.request.OrderRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.OrderResponse;
import vn.eledevo.vksbe.entity.Customer;
import vn.eledevo.vksbe.entity.Employee;
import vn.eledevo.vksbe.entity.Order;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.mapper.OrderMapper;
import vn.eledevo.vksbe.repository.CustomerRepository;
import vn.eledevo.vksbe.repository.EmployeeRepository;
import vn.eledevo.vksbe.repository.OrderRepository;

import java.util.List;
import java.util.stream.Collectors;

import static vn.eledevo.vksbe.constant.ResponseMessage.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    CustomerRepository customerRepository;

    EmployeeRepository employeeRepository;

    OrderRepository orderRepository;

    OrderMapper orderMapper;
    @Override
    public ApiResponse<List<OrderResponse>> getAllOrder() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderResponse> orderResponseList = orderList
                .stream()
                .map(orderMapper::mapEntityToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(200, "Lấy dữ liệu thành công", orderResponseList);
    }

    @Override
    public ApiResponse<List<OrderResponse>> getOrder(Order textSearch, String sortField, String sortDirection, int currentPage, int limitPage) {
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
        List<OrderResponse> orderResponseList = orderList
                .stream()
                .map(orderMapper::mapEntityToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(200, "Lấy dữ liệu thành công", orderResponseList);
    }


    @Override
    public ApiResponse<Order> addOrder(OrderRequest orderRequest) throws ValidationException {
        Order order = orderMapper.mapRequestToEntity(orderRequest);
        if (orderRequest.getEmployee() == null || orderRequest.getCustomer() == null){
            throw new ValidationException("Order", ORDER_BLANK);
        } else if (!employeeRepository.existsById(orderRequest.getEmployee())){
            throw new ValidationException("Employee", EMPLOYEE_NOT_EXIST);
        } else if (!customerRepository.existsById(orderRequest.getCustomer())){
            throw new ValidationException("Customer", CUSTOMER_NOT_EXIST);
        }
        Employee employee = employeeRepository.findById(orderRequest.getEmployee()).get();
        Customer customer = customerRepository.findById(orderRequest.getCustomer()).get();
        order.setEmployee(employee);
        order.setCustomer(customer);
        order.setEmployeeID(employee.getEmployeeID());
        order.setEmployeeName(employee.getName());
        order.setCustomerName(customer.getName());
        return new ApiResponse<>(200, "Thêm dữ liệu thành công", orderRepository.save(order));
    }

    @Override
    public ApiResponse<Order> updateOrder(Long id, OrderRequest orderRequest) throws ValidationException{
        if (!orderRepository.existsById(id)){
            throw new ValidationException("Order", ORDER_NOT_EXIST);
        }
        Order order = orderRepository.findById(id).get();
        order.setStatus(orderRequest.getStatus());
        return new ApiResponse<>(200, "Cập nhật dữ liệu thành công", orderRepository.save(order));
    }

    @Override
    public ApiResponse<Order> deleteOrder(Long id) throws ValidationException{
        if (!orderRepository.existsById(id)){
            throw new ValidationException("Order", ORDER_NOT_EXIST);
        }
        orderRepository.deleteById(id);
        return new ApiResponse<>(200, "Xóa dữ liệu thành công");
    }
}
