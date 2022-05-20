package uz.pdp.laptopservice.controller;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.laptopservice.entity.CartItem;
import uz.pdp.laptopservice.entity.ItemStatus;
import uz.pdp.laptopservice.repository.CartItemRepository;
import uz.pdp.laptopservice.service.PurchaseService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class PurchaseController {
    private final CartItemRepository cartItemRepository;
    private final PurchaseService purchaseService;

    @Value("${WEBHOOK_KEY}")
    private String webhookKey;
    @Value("${STRIPE_API_KEY}")
    private String stripeApiKey;


    //    @SneakyThrows
    @PostMapping("/webhook")
    public void handle(@RequestBody String payload, @RequestHeader(name = "Stripe-Signature") String signHeader, HttpServletResponse response) {
        String endpointSecret = webhookKey;
        Stripe.apiKey = stripeApiKey;
//      to activate:  stripe listen --forward-to localhost:8081/webhook


        Event event = null;
        try {
            event = Webhook.constructEvent(payload, signHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }

        System.out.println("Order fulfilled");
        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();
            purchaseService.fulfillOrder();
        }

    }

    @GetMapping("/api/purchase-service")
    public HttpEntity<?> createStripeSession() {

        Stripe.apiKey = stripeApiKey;
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        List<CartItem> cartItems = cartItemRepository.findAllByUserIdAndItemStatus(1, ItemStatus.NEW);

        return purchaseService.getStripeSession(lineItems, cartItems);
    }

}
