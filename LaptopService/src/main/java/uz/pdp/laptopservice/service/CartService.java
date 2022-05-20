package uz.pdp.laptopservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.laptopservice.common.ApiResponse;
import uz.pdp.laptopservice.dto.CartItemDto;
import uz.pdp.laptopservice.entity.CartItem;
import uz.pdp.laptopservice.entity.ItemStatus;
import uz.pdp.laptopservice.entity.Laptop;
import uz.pdp.laptopservice.repository.CartItemRepository;
import uz.pdp.laptopservice.repository.LaptopRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final LaptopRepository laptopRepository;

    public ApiResponse addLaptopToCart(CartItemDto cartItemDto, Integer userId) {
        Optional<Laptop> optionalLaptop = laptopRepository.findById(cartItemDto.getLaptopId());
        if (optionalLaptop.isEmpty()) return new ApiResponse("Laptop not found", 404);

        CartItem cartItem = new CartItem(null, userId, optionalLaptop.get(), cartItemDto.getQuantity(), ItemStatus.NEW);
        cartItemRepository.save(cartItem);
        return new ApiResponse("Successfully added", 200, cartItem);
    }

    public ApiResponse showItemsOnCart(Integer userId) {
        List<CartItem> items = cartItemRepository.findAllByUserIdAndItemStatus(userId, ItemStatus.NEW);
        return new ApiResponse("ok", 200, items);
    }

    @Transactional
    public ApiResponse clearItemsOnCart(Integer userId) {
        cartItemRepository.deleteAllByUserIdAndItemStatus(userId, ItemStatus.NEW);
        return new ApiResponse("ok", 200);
    }

    public ApiResponse showPurchasedLaptops(Integer userId) {
        List<CartItem> items = cartItemRepository.findAllByUserIdAndItemStatus(userId, ItemStatus.PURCHASED);
        return new ApiResponse("ok", 200, items);
    }
}
