package vn.eledevo.vksbe.mapper;

import org.mapstruct.Mapper;
import vn.eledevo.vksbe.dto.request.CustomerRequest;
import vn.eledevo.vksbe.dto.response.CustomerResponse;
import vn.eledevo.vksbe.entity.Customer;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper extends BaseMapper<CustomerRequest,CustomerResponse,Customer>{
}
