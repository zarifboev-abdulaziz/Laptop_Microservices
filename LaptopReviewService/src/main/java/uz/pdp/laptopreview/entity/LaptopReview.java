package uz.pdp.laptopreview.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "laptop_reviews")
public class LaptopReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer laptopId;
    private Integer userId;
    private String reviewBody;
    private Integer rate = 0;

    @OrderBy
    @CreationTimestamp
    @Column(nullable = false, updatable = false, columnDefinition = " timestamp default now() ")
    private LocalDateTime createdAt =LocalDateTime.now();


}
