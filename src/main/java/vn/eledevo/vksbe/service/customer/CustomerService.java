package vn.eledevo.vksbe.service.customer;

import vn.eledevo.vksbe.dto.request.CustomerRequest;
import vn.eledevo.vksbe.dto.response.CustomerResponse;
import vn.eledevo.vksbe.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getAllCustomer();
    List<CustomerResponse> getCustomer(Customer textSearch, String sortField, String sortDirection, int currentPage, int limitPage);
    Customer addCustomer(CustomerRequest customerRequest);
    Customer updateCustomer(Long id, CustomerRequest customerRequest);
    Customer deleteCustomer(Long id);
}
