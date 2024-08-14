package vn.eledevo.vksbe.mapper;

import org.mapstruct.Mapper;
import vn.eledevo.vksbe.dto.request.CustomerRequest;
import vn.eledevo.vksbe.dto.response.CustomerResponse;
import vn.eledevo.vksbe.entity.Customer;

@Mapper(componentModel = "spring")
public class CustomerMapper{
    public CustomerResponse mapEntityToResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setName(customer.getName());
        response.setAddress(customer.getAddress());
        response.setPhone(customer.getPhone());
        response.setStatus(customer.getStatus());
        response.setEmployee(customer.getEmployee());
        return response;
    }

    public Customer mapRequestToEntity(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhone(customerRequest.getPhone());
        customer.setStatus(customerRequest.getStatus());
        return customer;
    }
}
