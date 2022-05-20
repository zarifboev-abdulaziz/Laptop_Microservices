package uz.pdp.laptopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
    private List<PurchaseItemDto> purchaseItems;
    private String purchaseDate;
    private double totalAmount;
    private String receiverEmail;

}
