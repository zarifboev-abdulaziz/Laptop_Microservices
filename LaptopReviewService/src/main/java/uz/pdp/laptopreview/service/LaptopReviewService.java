package uz.pdp.laptopreview.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.laptopreview.common.ApiResponse;
import uz.pdp.laptopreview.entity.LaptopReview;
import uz.pdp.laptopreview.repository.LaptopReviewRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaptopReviewService {
    private final LaptopReviewRepository laptopReviewRepository;

    public List<LaptopReview> getLaptopReviewByLaptopId(Integer laptopId) {
        return laptopReviewRepository.findAllByLaptopId(laptopId);
    }

    public ApiResponse saveLaptopReview(LaptopReview laptopReview, Integer userId) {
        laptopReview.setCreatedAt(LocalDateTime.now());
        laptopReview.setUserId(userId);
        laptopReviewRepository.save(laptopReview);
        return new ApiResponse("Review successfully saved", true, laptopReview);
    }

    public ResponseEntity<?> delete(Integer id) {
        try {
            laptopReviewRepository.deleteById(id);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
    }
}
