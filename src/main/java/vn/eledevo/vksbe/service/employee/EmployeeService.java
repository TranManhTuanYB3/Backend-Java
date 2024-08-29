package vn.eledevo.vksbe.service.employee;

import vn.eledevo.vksbe.dto.request.EmployeeRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.EmployeeResponse;
import vn.eledevo.vksbe.entity.Customer;
import vn.eledevo.vksbe.entity.Employee;
import vn.eledevo.vksbe.exception.ValidationException;

import java.util.List;

public interface EmployeeService {
    ApiResponse<List<EmployeeResponse>> getAllEmployee();
    ApiResponse<List<EmployeeResponse>> getEmployee(Employee textSearch, String sortField, String sortDirection, int currentPage, int limitPage);
    ApiResponse<Employee> addEmployee(EmployeeRequest employeeRequest) throws ValidationException;
    ApiResponse<Employee> updateEmployee(Long id, EmployeeRequest employeeRequest) throws ValidationException;
    ApiResponse<Employee> deleteEmployee(Long id) throws ValidationException;

    ApiResponse<List<EmployeeResponse>> getUnassignedEmployees();
    ApiResponse<List<EmployeeResponse>> getUnassignedEmployeesExcluding(Long id);
}
