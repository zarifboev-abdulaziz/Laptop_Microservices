package uz.pdp.laptopservice.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.laptopservice.dto.PurchaseDto;
import uz.pdp.laptopservice.dto.PurchaseItemDto;
import uz.pdp.laptopservice.entity.CartItem;
import uz.pdp.laptopservice.entity.ItemStatus;
import uz.pdp.laptopservice.repository.CartItemRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final CartItemRepository cartItemRepository;
    private final RabbitTemplate rabbitTemplate;


    @Value("${spring.rabbitmq.exchange}")
    String exchange;
    @Value("${spring.rabbitmq.routingkey}")
    String routingKey;


    public ResponseEntity<?> getStripeSession(List<SessionCreateParams.LineItem> lineItems, List<CartItem> cartItems) {
        for (CartItem item : cartItems) {

            double productPrice = (item.getLaptop().getPrice() * 100 + 0.3) / (1 - 2.9 / 100);
            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData
                    .builder()
                    .setName(item.getLaptop().getName())
                    .build();

            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData
                    .builder()
                    .setProductData(productData)
                    .setCurrency("usd")
                    .setUnitAmount((long) (productPrice))
                    .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem
                    .builder()
                    .setPriceData(priceData)
                    .setQuantity((long) item.getQuantity())
                    .build();


            lineItems.add(lineItem);


        }

        SessionCreateParams params = SessionCreateParams
                .builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl("http://localhost:8080/failed")
                .setSuccessUrl("http://localhost:8080/success")
                .setClientReferenceId("1")
                .addAllLineItem(lineItems)
                .build();
        try {
            Session session = Session.create(params);
            String checkoutUrl = session.getUrl();

            return ResponseEntity.ok(checkoutUrl);

        } catch (StripeException e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().build();
    }

    public boolean fulfillOrder() {
        List<CartItem> cartItems = cartItemRepository.findAllByUserIdAndItemStatus(1, ItemStatus.NEW);

        if (cartItems.size() == 0) {
            return false;
        }

        List<PurchaseItemDto> purchaseItems = new ArrayList<>();
        double totalAmount = 0;
        for (CartItem item : cartItems) {
            item.setItemStatus(ItemStatus.PURCHASED);
            cartItemRepository.save(item);

            purchaseItems.add(
                    new PurchaseItemDto(item.getLaptop().getName(), item.getQuantity(), item.getLaptop().getPrice())
            );

            totalAmount += item.getQuantity() * item.getLaptop().getPrice();
        }

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setPurchaseItems(purchaseItems);
        purchaseDto.setPurchaseDate(LocalDate.now().toString());
        purchaseDto.setTotalAmount(totalAmount);
        purchaseDto.setReceiverEmail("abdulaziz2000go@gmail.com");

        sendEmailToCustomer(purchaseDto);
        return true;
    }

    public void sendEmailToCustomer(PurchaseDto purchaseDto) {
        rabbitTemplate.convertAndSend(exchange, routingKey, purchaseDto);
    }
}
