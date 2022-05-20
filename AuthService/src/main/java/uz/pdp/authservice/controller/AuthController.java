package uz.pdp.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.authservice.payload.ApiResponse;
import uz.pdp.authservice.payload.authPayloads.LoginDto;
import uz.pdp.authservice.payload.authPayloads.RegisterDto;
import uz.pdp.authservice.security.JwtFilter;
import uz.pdp.authservice.security.JwtProvider;
import uz.pdp.authservice.service.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        ApiResponse apiResponse = authService.registerUser(registerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }


    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginDto loginDto){
        ApiResponse apiResponse = authService.login(loginDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse);
    }

    @PostMapping("/validateToken")
    public HttpEntity<?> validateToken(@RequestParam(name = "token") String token){
        ApiResponse apiResponse = authService.validateToken(token);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 401).body(apiResponse.getObject());
    }

    @GetMapping("/get-user/{email}")
    public UserDetails getUserByUserName(@PathVariable String email){
        return authService.loadUserByUsername(email);
    }


}
