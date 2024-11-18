package uz.demo.jpa_library_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.demo.jpa_library_springboot.model.Floor;

public interface FloorRepository extends JpaRepository<Floor, Integer> {
    int countFloorByFloorName(String floorName);
}
