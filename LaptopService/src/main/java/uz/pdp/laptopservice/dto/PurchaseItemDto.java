package uz.pdp.laptopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemDto {
    private String laptopName;
    private int laptopQuantity;
    private double laptopPrice;
}
