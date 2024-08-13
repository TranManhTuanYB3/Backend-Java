package vn.eledevo.vksbe.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    Long id;
    String employeeID;
    String employeeName;
    String customerName;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    BigDecimal price;
    String status;
}
