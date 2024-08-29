package vn.eledevo.vksbe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.eledevo.vksbe.dto.request.EmployeeRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.EmployeeResponse;
import vn.eledevo.vksbe.entity.Employee;
import vn.eledevo.vksbe.exception.ValidationException;
import vn.eledevo.vksbe.service.employee.EmployeeService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployee(){
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getEmployee(
            @RequestBody Employee textSearch,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "0") int currentPage,
            @RequestParam(defaultValue = "3") int limitPage
    ){
        return ResponseEntity.ok(employeeService.getEmployee(textSearch, sortField, sortDirection, currentPage, limitPage));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Employee>> addEmployee(@RequestBody EmployeeRequest employeeRequest) throws ValidationException {
        return ResponseEntity.ok(employeeService.addEmployee(employeeRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest) throws ValidationException {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeRequest));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<ApiResponse<Employee>> deleteCustomer(@PathVariable Long id) throws ValidationException {
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }

    @GetMapping("/getnoid")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getUnassignedEmployees() {
        return ResponseEntity.ok(employeeService.getUnassignedEmployees());
    }

    @GetMapping("/getid")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getUnassignedEmployees(@RequestParam Long id) {
        return ResponseEntity.ok(employeeService.getUnassignedEmployeesExcluding(id));
    }
}
