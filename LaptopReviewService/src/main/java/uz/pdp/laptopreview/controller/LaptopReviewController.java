package uz.pdp.laptopreview.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.laptopreview.common.ApiResponse;
import uz.pdp.laptopreview.entity.LaptopReview;
import uz.pdp.laptopreview.service.LaptopReviewService;

import java.util.List;


@RestController
@RequestMapping("/api/laptop-review-service")
@RequiredArgsConstructor
public class LaptopReviewController {
    private final LaptopReviewService laptopReviewService;

    @GetMapping("/{laptopId}")
    public List<LaptopReview> getLaptopReviewsByLaptopId(@PathVariable Integer laptopId) {
        return laptopReviewService.getLaptopReviewByLaptopId(laptopId);
    }

    @PostMapping
    public ResponseEntity<?> saveLaptopReview(@RequestBody LaptopReview laptopReview, @RequestHeader(name = "current-user-id", defaultValue = "1") Integer userId) {
        ApiResponse apiResponse = laptopReviewService.saveLaptopReview(laptopReview, userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 202 : 409).body(apiResponse.getObject());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return laptopReviewService.delete(id);
    }

}
