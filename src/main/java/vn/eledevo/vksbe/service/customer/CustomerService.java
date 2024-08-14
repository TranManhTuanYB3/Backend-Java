package vn.eledevo.vksbe.service.customer;

import vn.eledevo.vksbe.dto.request.CustomerRequest;
import vn.eledevo.vksbe.dto.response.ApiResponse;
import vn.eledevo.vksbe.dto.response.CustomerResponse;
import vn.eledevo.vksbe.entity.Customer;
import vn.eledevo.vksbe.exception.ValidationException;

import java.util.List;

public interface CustomerService {
    ApiResponse<List<CustomerResponse>> getAllCustomer();
    ApiResponse<List<CustomerResponse>> getCustomer(Customer textSearch, String sortField, String sortDirection, int currentPage, int limitPage);
    ApiResponse<Customer> addCustomer(CustomerRequest customerRequest) throws ValidationException;
    ApiResponse<Customer> updateCustomer(Long id, CustomerRequest customerRequest) throws ValidationException;
    ApiResponse<Customer> deleteCustomer(Long id) throws ValidationException;
}
