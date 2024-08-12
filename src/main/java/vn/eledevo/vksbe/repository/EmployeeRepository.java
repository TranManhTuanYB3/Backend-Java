package vn.eledevo.vksbe.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.eledevo.vksbe.dto.response.EmployeeResponse;
import vn.eledevo.vksbe.entity.Employee;

import java.util.List;

public interface EmployeeRepository extends BaseRepository<Employee, Long>{
    @Query("SELECT e FROM Employee e " +
            "WHERE (:employeeID IS NULL OR e.employeeID LIKE %:employeeID%) " +
            "AND (:name IS NULL OR e.name LIKE %:name%) " +
            "AND (:address IS NULL OR e.address LIKE %:address%) " +
            "AND (:phone IS NULL OR e.phone LIKE %:phone%) ")
    List<Employee> searchEmployees(@Param("employeeID") String employeeID,
                                           @Param("name") String name,
                                           @Param("address") String address,
                                           @Param("phone") String phone,
                                           Pageable pageable);

}
