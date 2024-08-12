package vn.eledevo.vksbe.mapper;

import org.mapstruct.Mapper;
import vn.eledevo.vksbe.dto.request.EmployeeRequest;
import vn.eledevo.vksbe.dto.response.EmployeeResponse;
import vn.eledevo.vksbe.entity.Employee;

@Mapper(componentModel = "spring")
public abstract class EmployeeMapper extends BaseMapper<EmployeeRequest, EmployeeResponse, Employee>{
}
