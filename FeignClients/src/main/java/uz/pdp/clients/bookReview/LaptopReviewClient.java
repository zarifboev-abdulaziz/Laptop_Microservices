package uz.pdp.clients.bookReview;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("laptop-review-service")
public interface LaptopReviewClient {
    @GetMapping("/api/laptop-review-service/{laptopId}")
    List<LaptopReview> getLaptopReviews(@PathVariable Integer laptopId);

//    @GetMapping("/api/laptop-review-service/average-rating/{laptopId}")
//    Double getLaptopAverageRating(@PathVariable Integer laptopId);

}
