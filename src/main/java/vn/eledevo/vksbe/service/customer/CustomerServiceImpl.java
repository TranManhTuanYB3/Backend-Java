package vn.eledevo.vksbe.service.customer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.eledevo.vksbe.dto.request.CustomerRequest;
import vn.eledevo.vksbe.dto.response.CustomerResponse;
import vn.eledevo.vksbe.entity.Customer;
import vn.eledevo.vksbe.mapper.CustomerMapper;
import vn.eledevo.vksbe.repository.CustomerRepository;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    CustomerRepository customerRepository;

    CustomerMapper customerMapper;

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerResponse> customerResponseList = customerMapper.toListResponse(customerList);
        return customerResponseList;
    }

    @Override
    public List<CustomerResponse> getCustomer(Customer textSearch, String sortField, String sortDirection, int currentPage, int limitPage) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sortDirection.equalsIgnoreCase("desc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(currentPage, limitPage, sort);
        List<Customer> customerList = customerRepository.searchCustomers(
                textSearch.getName(),
                textSearch.getAddress(),
                textSearch.getPhone(),
                textSearch.getStatus(),
                pageable
        );
        List<CustomerResponse> customerResponseList = customerMapper.toListResponse(customerList);
        return customerResponseList;
    }

    @Override
    public Customer addCustomer(CustomerRequest customerRequest) {
        Customer customer = customerMapper.toEntity(customerRequest);
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(id).get();
        customer.setName(customerRequest.getName());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhone(customerRequest.getPhone());
        customer.setStatus(customerRequest.getStatus());
        customer.setEmployee(customerRequest.getEmployee());
        return customerRepository.save(customer);
    }

    @Override
    public Customer deleteCustomer(Long id) {
        customerRepository.deleteById(id);
        return null;
    }

}
