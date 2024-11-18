package uz.demo.jpa_library_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.demo.jpa_library_springboot.model.Closet;

import java.util.Optional;

public interface ClosetRepository extends JpaRepository<Closet, Integer> {
    int countClosetById(String id);

    Optional<Closet> findClosetById(String id);
}
