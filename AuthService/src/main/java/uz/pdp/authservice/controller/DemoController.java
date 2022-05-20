package uz.pdp.authservice.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/user")
    public HttpEntity<?> demo() {
        return ResponseEntity.status(200).body("Successful Response");
    }

    @GetMapping("/admin")
    public HttpEntity<?> demoMethod() {
        return ResponseEntity.status(200).body("Successful Response");
    }

}
