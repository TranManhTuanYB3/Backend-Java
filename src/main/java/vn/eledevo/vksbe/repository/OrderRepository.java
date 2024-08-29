package vn.eledevo.vksbe.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.eledevo.vksbe.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends BaseRepository<Order, Long>{
    @Query("SELECT o FROM Order o " +
            "WHERE (:employeeID IS NULL OR o.employeeID LIKE %:employeeID%) " +
            "AND (:employeeName IS NULL OR o.employeeName LIKE %:employeeName%) " +
            "AND (:customerName IS NULL OR o.customerName LIKE %:customerName%) " +
            "AND (:fromDate IS NULL OR o.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR o.createdAt <= :toDate) " +
            "AND (:minPrice IS NULL OR o.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR o.price <= :maxPrice) " +
            "AND (:status IS NULL OR o.status LIKE %:status%)")
    List<Order> searchOrders(@Param("employeeID") String employeeID,
                             @Param("employeeName") String employeeName,
                             @Param("customerName") String customerName,
                             @Param("fromDate") LocalDate fromDate,
                             @Param("toDate") LocalDate toDate,
                             @Param("minPrice") BigDecimal minPrice,
                             @Param("maxPrice") BigDecimal maxPrice,
                             @Param("status") String status,
                             Pageable pageable);

    @Query("SELECT count(*) FROM Order o " +
            "WHERE (:employeeID IS NULL OR o.employeeID LIKE %:employeeID%) " +
            "AND (:employeeName IS NULL OR o.employeeName LIKE %:employeeName%) " +
            "AND (:customerName IS NULL OR o.customerName LIKE %:customerName%) " +
            "AND (:fromDate IS NULL OR o.createdAt >= :fromDate) " +
            "AND (:toDate IS NULL OR o.createdAt <= :toDate) " +
            "AND (:minPrice IS NULL OR o.price >= :minPrice) " +
            "AND (:maxPrice IS NULL OR o.price <= :maxPrice) " +
            "AND (:status IS NULL OR o.status LIKE %:status%)")
    long totalRecords(@Param("employeeID") String employeeID,
                             @Param("employeeName") String employeeName,
                             @Param("customerName") String customerName,
                             @Param("fromDate") LocalDate fromDate,
                             @Param("toDate") LocalDate toDate,
                             @Param("minPrice") BigDecimal minPrice,
                             @Param("maxPrice") BigDecimal maxPrice,
                             @Param("status") String status);
    List<Order> findByEmployeeId(Long id);
    List<Order> findByCustomerId(Long id);

    boolean existsById(Long id);
}
