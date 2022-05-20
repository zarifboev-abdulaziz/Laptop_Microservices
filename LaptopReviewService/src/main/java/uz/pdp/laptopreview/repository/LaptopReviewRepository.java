package uz.pdp.laptopreview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.laptopreview.entity.LaptopReview;

import java.util.List;

public interface LaptopReviewRepository extends JpaRepository<LaptopReview, Integer> {

    List<LaptopReview> findAllByLaptopId(Integer laptopId);

}
