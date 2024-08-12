package vn.eledevo.vksbe.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.eledevo.vksbe.dto.request.EmployeeRequest;
import vn.eledevo.vksbe.dto.response.EmployeeResponse;
import vn.eledevo.vksbe.entity.Employee;
import vn.eledevo.vksbe.service.employee.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployee(){
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @PostMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> getEmployee(
            @RequestBody Employee textSearch,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "") String sortDirection,
            @RequestParam(defaultValue = "0") int currentPage,
            @RequestParam(defaultValue = "3") int limitPage
    ){
        return ResponseEntity.ok(employeeService.getEmployee(textSearch, sortField, sortDirection, currentPage, limitPage));
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeRequest employeeRequest){
        return ResponseEntity.ok(employeeService.addEmployee(employeeRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequest employeeRequest){
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeRequest));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Employee> deleteCustomer(@PathVariable Long id){
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}
