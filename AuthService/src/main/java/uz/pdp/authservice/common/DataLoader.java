package uz.pdp.authservice.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.authservice.entity.Permission;
import uz.pdp.authservice.entity.Role;
import uz.pdp.authservice.entity.User;
import uz.pdp.authservice.repository.PermissionRepository;
import uz.pdp.authservice.repository.RoleRepository;
import uz.pdp.authservice.repository.UserRepository;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.sql.init.mode}")
    public String initMode;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("never")) {
            return;
        }

        Role roleAdmin = roleRepository.save(new Role(null, "ROLE_ADMIN"));
        Role roleUser = roleRepository.save(new Role(null, "ROLE_USER"));
        User admin1 = userRepository.save(new User(null, "Admin1", "admin", passwordEncoder.encode("123"),
                new HashSet<>(Collections.singletonList(roleAdmin))));
        User user1 = userRepository.save(new User(null, "User", "user", passwordEncoder.encode("123"),
                new HashSet<>(Collections.singletonList(roleUser))));


    }


}
