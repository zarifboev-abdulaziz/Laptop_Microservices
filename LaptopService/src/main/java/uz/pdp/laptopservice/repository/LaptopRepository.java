package uz.pdp.laptopservice.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.laptopservice.entity.Laptop;

import java.util.List;

public interface LaptopRepository extends JpaRepository<Laptop, Integer> {

    @Query(nativeQuery = true, value = "select l.* from laptops l where lower(l.name) like lower(concat('%', :search, '%'))")
    List<Laptop> getAllBooks(Pageable pageable, String search);

//    boolean existsByTitle(String title);



}
