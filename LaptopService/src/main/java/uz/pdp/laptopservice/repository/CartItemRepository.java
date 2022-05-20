package uz.pdp.laptopservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.laptopservice.entity.CartItem;
import uz.pdp.laptopservice.entity.ItemStatus;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findAllByUserIdAndItemStatus(Integer userId, ItemStatus itemStatus);

    void deleteAllByUserIdAndItemStatus(Integer userId, ItemStatus itemStatus);

}

