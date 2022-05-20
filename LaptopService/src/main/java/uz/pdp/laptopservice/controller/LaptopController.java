package uz.pdp.laptopservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clients.bookReview.LaptopReviewClient;
import uz.pdp.laptopservice.common.ApiResponse;
import uz.pdp.laptopservice.dto.LaptopDto;
import uz.pdp.laptopservice.repository.LaptopRepository;
import uz.pdp.laptopservice.service.LaptopService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/laptop-service")
@RequiredArgsConstructor
public class LaptopController {
    private final LaptopService laptopService;
    private final LaptopRepository laptopRepository;

    @GetMapping("/view")
    public HttpEntity<?> getAllLaptops(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "") String search) {
        List<Map<String, Object>> bookMap = laptopService.getAllLaptops(page, size, search);
        return ResponseEntity.status(200).body(bookMap);
    }


    @GetMapping("/view/{laptopId}")
    public HttpEntity<?> getLaptopById(@PathVariable Integer laptopId) {
        ApiResponse apiResponse = laptopService.getLaptopById(laptopId);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse.getObject());
    }

    @GetMapping("/reviews/{laptopId}")
    public HttpEntity<?> getLaptopReviews(@PathVariable Integer laptopId) {
        ApiResponse apiResponse = laptopService.getAllLaptopReviews(laptopId);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


    @PostMapping()
    public HttpEntity<?> addLaptop(@RequestBody LaptopDto laptopDto) {
        ApiResponse apiResponse = laptopService.saveNewLaptop(laptopDto);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @DeleteMapping("/{laptopId}")
    public HttpEntity<?> deleteLaptop(@PathVariable Integer laptopId) {
        if (!laptopRepository.existsById(laptopId)) return ResponseEntity.status(404).body("Laptop not found");

        try {
            laptopRepository.deleteById(laptopId);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(409).body("Error in deleting");
        }
        return ResponseEntity.status(200).body("Successfully deleted");
    }

}
