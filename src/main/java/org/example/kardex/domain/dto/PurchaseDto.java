package org.example.kardex.domain.dto;

import lombok.*;
import org.example.kardex.domain.Customer;
import org.example.kardex.domain.PurchaseDetail;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    private static final long serialVersionUID = 4329041851852897478L;
    private Long id;
    private Double total;
    private Customer customer;
    private List<PurchaseDetail> purchaseDetailList;

}
