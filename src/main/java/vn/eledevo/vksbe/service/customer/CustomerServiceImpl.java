package vn.eledevo.vksbe.service.customer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.dto.request.CustomerRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.CustomerResponse;
import vn.eledevo.vksbe.entity.Customer;
import vn.eledevo.vksbe.entity.Order;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.mapper.CustomerMapper;
import vn.eledevo.vksbe.repository.CustomerRepository;
import vn.eledevo.vksbe.repository.EmployeeRepository;
import vn.eledevo.vksbe.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static vn.eledevo.vksbe.constant.ResponseMessage.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    OrderRepository orderRepository;

    EmployeeRepository employeeRepository;

    CustomerRepository customerRepository;

    CustomerMapper customerMapper;

    @Override
    public ApiResponse<List<CustomerResponse>> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerResponse> customerResponseList = customerList
                .stream()
                .map(customerMapper::mapEntityToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(200, "Lấy dữ liệu thành công", customerResponseList);
    }

    @Override
    public ApiResponse<List<CustomerResponse>> getCustomer(Customer textSearch, String sortField, String sortDirection, int currentPage, int limitPage) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(currentPage, limitPage, sort);
        List<Customer> customerList;
        long totalRecords;
        if (textSearch.getEmployee()==null) {
             customerList = customerRepository.searchCustomers(
                    textSearch.getName(),
                    textSearch.getAddress(),
                    textSearch.getPhone(),
                    textSearch.getStatus(),
                    pageable
            );
            totalRecords = customerRepository.totalRecordsCustomer(
                    textSearch.getName(),
                    textSearch.getAddress(),
                    textSearch.getPhone(),
                    textSearch.getStatus()
            );
        } else {
            customerList = customerRepository.searchEmployee(
                    textSearch.getName(),
                    textSearch.getAddress(),
                    textSearch.getPhone(),
                    textSearch.getStatus(),
                    textSearch.getEmployee().getName(),
                    pageable
            );
            totalRecords = customerRepository.totalRecordsEmployee(
                    textSearch.getName(),
                    textSearch.getAddress(),
                    textSearch.getPhone(),
                    textSearch.getStatus(),
                    textSearch.getEmployee().getName()
            );
        }
        List<CustomerResponse> customerResponseList = customerList
                .stream()
                .map(customerMapper::mapEntityToResponse)
                .collect(Collectors.toList());
        return new ApiResponse<>(200, "Lấy dữ liệu thành công", customerResponseList, totalRecords);
    }

    @Override
    public ApiResponse<Customer> addCustomer(CustomerRequest customerRequest) throws ValidationException{
        if (customerRepository.existsByPhone(customerRequest.getPhone())) {
            throw new ValidationException("Phone", PHONE_EXIST);
        }

        Customer customer = customerMapper.mapRequestToEntity(customerRequest);
        if (customerRequest.getEmployee() == null){
            customer.setEmployee(null);
        } else if (customerRepository.existsByEmployeeId(customerRequest.getEmployee().getId())){
            throw new ValidationException("Employee", EMPLOYEE_EXIST);
        } else if (!employeeRepository.existsById(customerRequest.getEmployee().getId())){
            throw new ValidationException("Employee", EMPLOYEE_NOT_EXIST);
        } else {
            customer.setEmployee(customerRequest.getEmployee());
        }
        return new ApiResponse<>(200, "Thêm dữ liệu thành công", customerRepository.save(customer));
    }

    @Override
    public ApiResponse<Customer> updateCustomer(Long id, CustomerRequest customerRequest) throws ValidationException {
        if (!customerRepository.existsById(id)){
            throw new ValidationException("Customer", CUSTOMER_NOT_EXIST);
        }

        if (customerRepository.existsByPhoneAndIdNot(customerRequest.getPhone(), id)) {
            throw new ValidationException("Phone", PHONE_EXIST);
        }

        Customer customer = customerRepository.findById(id).get();
        if (customerRequest.getEmployee() == null){
            customer.setEmployee(null);
        } else if (customerRepository.existsByEmployeeIdAndIdNot(customerRequest.getEmployee().getId(), id)){
            throw new ValidationException("Employee", EMPLOYEE_EXIST);
        } else if (!employeeRepository.existsById(customerRequest.getEmployee().getId())){
            throw new ValidationException("Employee", EMPLOYEE_NOT_EXIST);
        } else {
            customer.setEmployee(customerRequest.getEmployee());
        }
        customer.setName(customerRequest.getName());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhone(customerRequest.getPhone());
        customer.setStatus(customerRequest.getStatus());
        List<Order> orders = orderRepository.findByCustomerId(id);
        for (Order order : orders) {
            order.setCustomerName(customerRequest.getName());
            orderRepository.save(order);
        }
        return new ApiResponse<>(200, "Cập nhật dữ liệu thành công", customerRepository.save(customer));
    }

    @Override
    public ApiResponse<Customer> deleteCustomer(Long id) throws ValidationException {
        if (!customerRepository.existsById(id)){
            throw new ValidationException("Customer", CUSTOMER_NOT_EXIST);
        }
        List<Order> orders = orderRepository.findByCustomerId(id);
        for (Order order : orders) {
            order.setCustomer(null);
            orderRepository.save(order);
        }
        customerRepository.deleteById(id);
        return new ApiResponse<>(200, "Xóa dữ liệu thành công");
    }

}
