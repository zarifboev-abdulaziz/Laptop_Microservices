package uz.pdp.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.authservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
