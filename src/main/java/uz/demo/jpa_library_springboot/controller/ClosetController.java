package uz.demo.jpa_library_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.demo.jpa_library_springboot.model.Closet;
import uz.demo.jpa_library_springboot.model.Floor;
import uz.demo.jpa_library_springboot.model.dtos.ClosetDto;
import uz.demo.jpa_library_springboot.repository.ClosetRepository;
import uz.demo.jpa_library_springboot.repository.FloorRepository;
import uz.demo.jpa_library_springboot.server.Result;

import java.util.List;
import java.util.Optional;

@RestController
public class ClosetController {

    @Autowired
    ClosetRepository closetRepository;

    @Autowired
    FloorRepository floorRepository;

    @GetMapping("/library/closet")
    public List<Closet> getClosets() {
        return closetRepository.findAll();
    }

    @GetMapping("/library/closet/{id}")
    public Closet getCloset(@PathVariable String  id) {
        Optional<Closet> optionalCloset = closetRepository.findClosetById(id);
        return optionalCloset.orElse(null);
    }

    @DeleteMapping("/library/closer-delete")
    public Result deleteCloset(@PathVariable Integer id) {
        Optional<Closet> optionalCloset = closetRepository.findById(id);
        if (optionalCloset.isPresent()) {
            closetRepository.deleteById(id);
            return new Result("Closet successfully deleted", true);
        }
        return new Result("Closet with this id does not exist", true);
    }

    @PostMapping("/library/closet")
    public Result addCloset(@RequestBody ClosetDto closetDto) {
        int count = closetRepository.countClosetById(closetDto.getId());
        if (count == 0) {
            Closet closet = new Closet();
            Floor floor = floorRepository.findById(closetDto.getFloorId()).orElse(null);
            if (floor != null) {
                closet.setFloor(floor);
            }
            closet.setId(closetDto.getId());
            closetRepository.save(closet);
            return new Result("Closet successfully added", true);
        }
        return new Result("Closet with this id already exists", false);
    }

    @PutMapping("/library/closer-edit")
    public Result updateCloset(@RequestBody ClosetDto closetDto) {
        int count = closetRepository.countClosetById(closetDto.getId());
        if (count == 0) {
            Closet closet = new Closet();
            closet.setId(closetDto.getId());
            closet.setFloor(floorRepository.findById(closetDto.getFloorId()).orElse(null));
            return new Result("Closet successfully updated", true);
        }
        return new Result("Closet with this id already exists", false);
    }
}
