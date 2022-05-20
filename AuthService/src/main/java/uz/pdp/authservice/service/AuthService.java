package uz.pdp.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.authservice.entity.User;
import uz.pdp.authservice.payload.ApiResponse;
import uz.pdp.authservice.payload.UserDto;
import uz.pdp.authservice.payload.authPayloads.LoginDto;
import uz.pdp.authservice.payload.authPayloads.RegisterDto;
import uz.pdp.authservice.repository.RoleRepository;
import uz.pdp.authservice.repository.UserRepository;
import uz.pdp.authservice.security.JwtProvider;


import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public ApiResponse registerUser(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail()))
            return new ApiResponse("This email is already exists", false);

        User user = new User();
        user.setFullName(registerDto.getFullName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);

        return new ApiResponse("Successfully Registered.", true);
    }


    public ApiResponse login(LoginDto loginDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword()
            ));

            User principal = (User) authentication.getPrincipal();
            String generatedToken = "Bearer " + jwtProvider.generateToken(principal.getEmail(), principal.getRoles());

            return new ApiResponse("Jwt Token is generated", true, generatedToken);
        } catch (BadCredentialsException e) {
            return new ApiResponse("Email or password not found", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


    public ApiResponse validateToken(String token) {
        String emailFromToken = jwtProvider.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(emailFromToken);
        if (optionalUser.isEmpty()) return new ApiResponse("email not found", false);

        return new ApiResponse("Ok", true, new UserDto(optionalUser.get().getId(), optionalUser.get().getUsername(), token));
    }
}

