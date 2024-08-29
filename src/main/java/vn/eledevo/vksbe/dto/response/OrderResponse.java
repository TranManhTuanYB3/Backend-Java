package vn.eledevo.vksbe.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    LocalDate createdAt;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime updatedAt;
    BigDecimal price;
    String status;
}
