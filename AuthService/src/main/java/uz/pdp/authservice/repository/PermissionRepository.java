package uz.pdp.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.authservice.entity.Permission;


public interface PermissionRepository extends JpaRepository<Permission, Integer> {


}
