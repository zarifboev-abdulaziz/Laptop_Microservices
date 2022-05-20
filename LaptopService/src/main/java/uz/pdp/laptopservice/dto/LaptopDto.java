package uz.pdp.laptopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LaptopDto {
    private String name;
    private String description;
    private Double price;
    private Integer photo_id;

    @ManyToMany
    private Set<Integer> characteristics;
}
