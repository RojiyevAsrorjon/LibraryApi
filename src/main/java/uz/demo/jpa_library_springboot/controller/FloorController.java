package uz.demo.jpa_library_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.demo.jpa_library_springboot.model.Floor;
import uz.demo.jpa_library_springboot.repository.FloorRepository;
import uz.demo.jpa_library_springboot.server.Result;

import java.util.List;
import java.util.Optional;

@RestController
public class FloorController {

    @Autowired
    private FloorRepository floorRepository;

    @GetMapping("/library/floor")
    public List<Floor> getFloors() {
        return floorRepository.findAll();
    }

    @GetMapping("/library/floor/{id}")
    public Floor getFloor(@PathVariable Integer id) {
        Optional<Floor> optionalFloor = floorRepository.findById(id);
        return optionalFloor.orElse(null);
    }

    @PostMapping("/library/floor")
    public Result addFloor(@RequestBody Floor floor) {
        int count = floorRepository.countFloorByFloorName(floor.getFloorName());
        if (count!=0) {
            return new Result("Floor with this name already exists", false);
        }
        floorRepository.save(floor);
        return new Result("Floor successfully added", true);
    }

    @DeleteMapping("/library/floor-delete/{id}")
    public Result deleteFloor(@PathVariable Integer id) {
        Optional<Floor> optionalFloor = floorRepository.findById(id);
        if (optionalFloor.isPresent()) {
            floorRepository.deleteById(id);
            return new Result("Floor successfully deleted", true);
        } else {
            return new Result("Floor with this id does not exist", false);
        }
    }

    @PutMapping("/library/floor-edit/{id}")
    public Result deleteFloor(@RequestBody Floor floor, @PathVariable Integer id) {
        Optional<Floor> optionalFloor = floorRepository.findById(id);
        if (optionalFloor.isPresent()) {
            floor.setId(id);
            floorRepository.save(floor);
            return new Result("Floor successfully updated", true);
        }
        return new Result("Floor with this id does not exist", false);
    }
}
