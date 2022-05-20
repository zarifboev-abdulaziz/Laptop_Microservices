package uz.pdp.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.authservice.entity.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
