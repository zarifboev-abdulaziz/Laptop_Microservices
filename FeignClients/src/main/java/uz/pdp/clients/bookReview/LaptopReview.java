package uz.pdp.clients.bookReview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class LaptopReview {
    private Integer id;
    private Integer laptopId;
    private Integer userId;
    private String reviewBody;
    private Integer rate;
    private LocalDateTime createdAt;

}
