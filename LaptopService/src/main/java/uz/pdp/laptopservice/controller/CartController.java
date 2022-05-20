package uz.pdp.laptopservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.laptopservice.common.ApiResponse;
import uz.pdp.laptopservice.dto.CartItemDto;
import uz.pdp.laptopservice.service.CartService;

@RestController
@RequestMapping("/api/cart-service")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public HttpEntity<?> addLaptopToCart(@RequestBody CartItemDto cartItemDto, @RequestHeader(name = "current_user_id", defaultValue = "1") Integer userId) {
        ApiResponse apiResponse = cartService.addLaptopToCart(cartItemDto, userId);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @GetMapping("/show")
    public HttpEntity<?> showItemsOnCart(@RequestHeader(name = "current_user_id", defaultValue = "1") Integer userId) {
        ApiResponse apiResponse = cartService.showItemsOnCart(userId);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @GetMapping("/clear")
    public HttpEntity<?> clearItemsOnCart(@RequestHeader(name = "current_user_id", defaultValue = "1") Integer userId) {
        ApiResponse apiResponse = cartService.clearItemsOnCart(userId);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }

    @GetMapping("/purchased-laptops")
    public HttpEntity<?> showPurchasedLaptops(@RequestHeader(name = "current_user_id", defaultValue = "1") Integer userId) {
        ApiResponse apiResponse = cartService.showPurchasedLaptops(userId);
        return ResponseEntity.status(apiResponse.getStatusCode()).body(apiResponse);
    }


}
