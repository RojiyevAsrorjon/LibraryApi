package uz.demo.jpa_library_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.demo.jpa_library_springboot.model.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Integer> {
}
