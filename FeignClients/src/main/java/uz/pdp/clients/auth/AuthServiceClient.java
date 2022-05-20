//package uz.pdp.clients.auth;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import uz.pdp.clients.bookReview.LaptopReview;
//
//import java.util.List;
//
//@FeignClient("auth-service")
//public interface AuthServiceClient {
//    @GetMapping("/api/auth/get-user/{email}")
//    UserDetails getUserByUsername(@PathVariable String email);
//
//
//}
