package vn.eledevo.vksbe.service.employee;

import vn.eledevo.vksbe.dto.request.EmployeeRequest;
import vn.eledevo.vksbe.dto.response.EmployeeResponse;
import vn.eledevo.vksbe.entity.Customer;
import vn.eledevo.vksbe.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> getAllEmployee();
    List<EmployeeResponse> getEmployee(Employee textSearch, String sortField, String sortDirection, int currentPage, int limitPage);
    Employee addEmployee(EmployeeRequest employeeRequest);
    Employee updateEmployee(Long id, EmployeeRequest employeeRequest);
    Employee deleteEmployee(Long id);
}
