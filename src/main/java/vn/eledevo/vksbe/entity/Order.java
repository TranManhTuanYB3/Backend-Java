package vn.eledevo.vksbe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "employee")
    Employee employee;
    String employeeID;
    String employeeName;

    @ManyToOne
    @JoinColumn(name = "customer")
    Customer customer;
    String customerName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime updatedAt;
    BigDecimal price;
    String status;

    @PrePersist
    void prePersist() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
