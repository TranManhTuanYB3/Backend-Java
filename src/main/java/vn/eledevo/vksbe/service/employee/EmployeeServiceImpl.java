package vn.eledevo.vksbe.service.employee;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.dto.request.EmployeeRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.EmployeeResponse;
import vn.eledevo.vksbe.entity.Customer;
import vn.eledevo.vksbe.entity.Employee;
import vn.eledevo.vksbe.entity.Order;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.mapper.EmployeeMapper;
import vn.eledevo.vksbe.repository.CustomerRepository;
import vn.eledevo.vksbe.repository.EmployeeRepository;
import vn.eledevo.vksbe.repository.OrderRepository;

import java.util.List;

import static vn.eledevo.vksbe.constant.ResponseMessage.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{
    OrderRepository orderRepository;

    CustomerRepository customerRepository;

    EmployeeRepository employeeRepository;

    EmployeeMapper employeeMapper;
    @Override
    public ApiResponse<List<EmployeeResponse>> getAllEmployee() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeResponse> employeeResponseList = employeeMapper.toListResponse(employeeList);
        return new ApiResponse<>(200, "Lấy dữ liệu thành công", employeeResponseList);
    }

    @Override
    public ApiResponse<List<EmployeeResponse>> getEmployee(Employee textSearch, String sortField, String sortDirection, int currentPage, int limitPage) {
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
        long totalRecords = employeeRepository.totalRecords(
                textSearch.getEmployeeID(),
                textSearch.getName(),
                textSearch.getAddress(),
                textSearch.getPhone()
        );
        return new ApiResponse<>(200, "Lấy dữ liệu thành công", employeeResponseList, totalRecords);
    }

    @Override
    public ApiResponse<Employee> addEmployee(EmployeeRequest employeeRequest) throws ValidationException {
        if (employeeRepository.existsByPhone(employeeRequest.getPhone())) {
            throw new ValidationException("Phone", PHONE_EXIST);
        }

        Employee employee = employeeMapper.toEntity(employeeRequest);
        employeeRepository.save(employee);
        if (employee.getId() < 10) {
            employee.setEmployeeID("NV0" + employee.getId());
        } else {
            employee.setEmployeeID("NV" + employee.getId());
        }
        return new ApiResponse<>(200, "Thêm dữ liệu thành công", employeeRepository.save(employee));
    }

    @Override
    public ApiResponse<Employee> updateEmployee(Long id, EmployeeRequest employeeRequest) throws ValidationException {
        if (employeeRepository.existsByPhoneAndIdNot(employeeRequest.getPhone(), id)) {
            throw new ValidationException("Phone", PHONE_EXIST);
        }

        if (!employeeRepository.existsById(id)){
            throw new ValidationException("Employee", EMPLOYEE_NOT_EXIST);
        }
        Employee employee = employeeRepository.findById(id).get();
        employee.setName(employeeRequest.getName());
        employee.setAddress(employeeRequest.getAddress());
        employee.setPhone(employeeRequest.getPhone());
        List<Order> orders = orderRepository.findByEmployeeId(id);
        for (Order order : orders) {
            order.setEmployeeName(employeeRequest.getName());
            orderRepository.save(order);
        }
        return new ApiResponse<>(200, "Cập nhật dữ liệu thành công", employeeRepository.save(employee));
    }

    @Override
    public ApiResponse<Employee> deleteEmployee(Long id) throws ValidationException {
        if (!employeeRepository.existsById(id)){
            throw new ValidationException("Employee", EMPLOYEE_NOT_EXIST);
        }
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
        return new ApiResponse<>(200, "Xóa dữ liệu thành công");
    }

    @Override
    public ApiResponse<List<EmployeeResponse>> getUnassignedEmployees() {
        List<Employee> employeeList = employeeRepository.findUnassignedEmployees();
        List<EmployeeResponse> employeeResponseList = employeeMapper.toListResponse(employeeList);
        return new ApiResponse<>(200, "Lấy dữ liệu thành công", employeeResponseList);
    }

    @Override
    public ApiResponse<List<EmployeeResponse>> getUnassignedEmployeesExcluding(Long id) {
        List<Employee> employeeList = employeeRepository.findUnassignedEmployeesExcluding(id);
        List<EmployeeResponse> employeeResponseList = employeeMapper.toListResponse(employeeList);
        return new ApiResponse<>(200, "Lấy dữ liệu thành công", employeeResponseList);
    }
}
