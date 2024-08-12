package vn.eledevo.vksbe.service.employee;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.dto.request.EmployeeRequest;
import vn.eledevo.vksbe.dto.response.EmployeeResponse;
import vn.eledevo.vksbe.entity.Customer;
import vn.eledevo.vksbe.entity.Employee;
import vn.eledevo.vksbe.entity.Order;
import vn.eledevo.vksbe.mapper.EmployeeMapper;
import vn.eledevo.vksbe.repository.CustomerRepository;
import vn.eledevo.vksbe.repository.EmployeeRepository;
import vn.eledevo.vksbe.repository.OrderRepository;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    OrderRepository orderRepository;

    CustomerRepository customerRepository;

    EmployeeRepository employeeRepository;

    EmployeeMapper employeeMapper;
    @Override
    public List<EmployeeResponse> getAllEmployee() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponseList = employeeMapper.toListResponse(employeeList);
        return employeeResponseList;
    }

    @Override
    public List<EmployeeResponse> getEmployee(Employee textSearch, String sortField, String sortDirection, int currentPage, int limitPage) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(currentPage, limitPage, sort);
        List<Employee> employeeList = employeeRepository.searchEmployees(
                textSearch.getEmployeeID(),
                textSearch.getName(),
                textSearch.getAddress(),
                textSearch.getPhone(),
                pageable
        );
        List<EmployeeResponse> employeeResponseList = employeeMapper.toListResponse(employeeList);
        return employeeResponseList;
    }

    @Override
    public Employee addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        employeeRepository.save(employee);
        if (employee.getId() < 10) {
            employee.setEmployeeID("NV0" + employee.getId());
        } else {
            employee.setEmployeeID("NV" + employee.getId());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(id).get();
        employee.setName(employeeRequest.getName());
        employee.setAddress(employeeRequest.getAddress());
        employee.setPhone(employeeRequest.getPhone());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee deleteEmployee(Long id) {
        List<Customer> customers = customerRepository.findByEmployeeId(id);
        for (Customer customer : customers) {
            customer.setEmployee(null);
            customerRepository.save(customer);
        }
        List<Order> orders = orderRepository.findByEmployeeId(id);
        for (Order order : orders) {
            order.setEmployee(null);
            orderRepository.save(order);
        }
        employeeRepository.deleteById(id);
        return null;
    }
}
