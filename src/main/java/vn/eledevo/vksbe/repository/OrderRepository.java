package vn.eledevo.vksbe.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.eledevo.vksbe.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends BaseRepository<Order, Long>{
    @Query("SELECT o FROM Order o " +
            "WHERE (:id IS NULL OR o.id = :id) " +
            "AND (:employeeName IS NULL OR o.employeeName LIKE %:employeeName%) " +
            "AND (:customerName IS NULL OR o.customerName LIKE %:customerName%) " +
            "AND (:createdAt IS NULL OR o.createdAt >= :createdAt) " +
            "AND (:updatedAt IS NULL OR o.updatedAt <= :updatedAt) " +
            "AND (:price IS NULL OR o.price = :price) " +
            "AND (:status IS NULL OR o.status LIKE %:status%)")
    List<Order> searchOrders(@Param("id") Long id,
                             @Param("employeeName") String employeeName,
                             @Param("customerName") String customerName,
                             @Param("createdAt") LocalDateTime createdAt,
                             @Param("updatedAt") LocalDateTime updatedAt,
                             @Param("price") BigDecimal price,
                             @Param("status") String status,
                             Pageable pageable);
    List<Order> findByEmployeeId(Long id);
    List<Order> findByCustomerId(Long id);
}
