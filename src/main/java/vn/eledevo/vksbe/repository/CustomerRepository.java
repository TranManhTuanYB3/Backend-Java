package vn.eledevo.vksbe.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.eledevo.vksbe.entity.Customer;
import vn.eledevo.vksbe.entity.Employee;

import java.util.List;

public interface CustomerRepository extends BaseRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c " +
            "WHERE (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:address IS NULL OR c.address LIKE %:address%) " +
            "AND (:phone IS NULL OR c.phone LIKE %:phone%) " +
            "AND (:status IS NULL OR c.status LIKE %:status%) ")
    List<Customer> searchCustomers(@Param("name") String name,
                                   @Param("address") String address,
                                   @Param("phone") String phone,
                                   @Param("status") String status,
                                   Pageable pageable);

    @Query("SELECT c FROM Customer c " +
            "WHERE (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:address IS NULL OR c.address LIKE %:address%) " +
            "AND (:phone IS NULL OR c.phone LIKE %:phone%) " +
            "AND (:status IS NULL OR c.status LIKE %:status%) " +
            "AND (:employee IS NULL OR c.employee.name LIKE %:employee%)")
    List<Customer> searchEmployee(@Param("name") String name,
                                   @Param("address") String address,
                                   @Param("phone") String phone,
                                   @Param("status") String status,
                                   @Param("employee") String employee,
                                   Pageable pageable);

    @Query("SELECT count(*) FROM Customer c " +
            "WHERE (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:address IS NULL OR c.address LIKE %:address%) " +
            "AND (:phone IS NULL OR c.phone LIKE %:phone%) " +
            "AND (:status IS NULL OR c.status LIKE %:status%) ")
    long totalRecordsCustomer(@Param("name") String name,
                              @Param("address") String address,
                              @Param("phone") String phone,
                              @Param("status") String status);

    @Query("SELECT count(*) FROM Customer c " +
            "WHERE (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:address IS NULL OR c.address LIKE %:address%) " +
            "AND (:phone IS NULL OR c.phone LIKE %:phone%) " +
            "AND (:status IS NULL OR c.status LIKE %:status%) " +
            "AND (:employee IS NULL OR c.employee.name LIKE %:employee%)")
    long totalRecordsEmployee(@Param("name") String name,
                                  @Param("address") String address,
                                  @Param("phone") String phone,
                                  @Param("status") String status,
                                  @Param("employee") String employee);

    List<Customer> findByEmployeeId(Long id);
    boolean existsByPhone(String phone);
    boolean existsByPhoneAndIdNot(String phone, Long id);
    boolean existsByEmployeeId(Long employee);
    boolean existsByEmployeeIdAndIdNot(Long employee, Long id);
    boolean existsById(Long id);
}
