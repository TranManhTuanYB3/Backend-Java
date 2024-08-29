package vn.eledevo.vksbe.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderTextSearchRequest {
    private String employeeID;
    private String employeeName;
    private String customerName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fromDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate toDate;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String status;
}
