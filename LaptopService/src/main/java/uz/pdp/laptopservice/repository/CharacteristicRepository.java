package uz.pdp.laptopservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.laptopservice.entity.Characteristic;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Integer> {

}
