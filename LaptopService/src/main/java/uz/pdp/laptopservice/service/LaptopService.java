package uz.pdp.laptopservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.clients.bookReview.LaptopReview;
import uz.pdp.clients.bookReview.LaptopReviewClient;
import uz.pdp.laptopservice.common.ApiResponse;
import uz.pdp.laptopservice.dto.LaptopDto;
import uz.pdp.laptopservice.entity.Characteristic;
import uz.pdp.laptopservice.entity.Laptop;
import uz.pdp.laptopservice.repository.CharacteristicRepository;
import uz.pdp.laptopservice.repository.LaptopRepository;


import java.util.*;

@Service
@RequiredArgsConstructor
public class LaptopService {
    private final LaptopRepository laptopRepository;
    private final CharacteristicRepository characteristicRepository;
    private final LaptopReviewClient laptopReviewClient;



    public List<Map<String, Object>> getAllLaptops(Integer page, Integer size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        List<Laptop> allLaptops = laptopRepository.getAllBooks(pageable, search);

        List<Map<String, Object>> laptopList = new ArrayList<>();
        for (Laptop laptop : allLaptops) {
            Map<String, Object> laptopMap = new HashMap<>();
            laptopMap.put("laptopId", laptop.getId());
            laptopMap.put("name", laptop.getName());
            laptopMap.put("attachmentId", laptop.getPhoto_id());
            laptopMap.put("description", laptop.getDescription());
//            bookMap.put("averageRating", averageRating);
            laptopList.add(laptopMap);
        }

        return laptopList;
    }

    public ApiResponse getLaptopById(Integer laptopId) {
        Optional<Laptop> optionalLaptop = laptopRepository.findById(laptopId);
        if (optionalLaptop.isEmpty()) return new ApiResponse("Laptop not found", 404);
        Laptop laptop = optionalLaptop.get();
        return new ApiResponse("Ok", 200, laptop);
    }


    public ApiResponse saveNewLaptop(LaptopDto laptopDto) {
        Set<Characteristic> characteristics = new HashSet<>();
        if(laptopDto.getCharacteristics().size() != 0){
            for (Integer characteristicId : laptopDto.getCharacteristics()) {
                Optional<Characteristic> optionalCharacteristic = characteristicRepository.findById(characteristicId);
                optionalCharacteristic.ifPresent(characteristics::add);
            }
        }

        Laptop laptop = new Laptop(
                null, laptopDto.getName(), laptopDto.getDescription(), laptopDto.getPrice(), laptopDto.getPhoto_id(), characteristics
        );
        laptopRepository.save(laptop);
        return new ApiResponse("Successfully saved", 202, laptop);
    }

    public ApiResponse getAllLaptopReviews(Integer laptopId) {
        List<LaptopReview> laptopReviews = laptopReviewClient.getLaptopReviews(laptopId);
        return new ApiResponse("ok", 200, laptopReviews);
    }
}
