package uz.demo.jpa_library_springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.demo.jpa_library_springboot.model.Closet;
import uz.demo.jpa_library_springboot.model.Shelf;
import uz.demo.jpa_library_springboot.model.dtos.ShelfDto;
import uz.demo.jpa_library_springboot.repository.ClosetRepository;
import uz.demo.jpa_library_springboot.repository.ShelfRepository;
import uz.demo.jpa_library_springboot.server.Result;

import java.util.List;
import java.util.Optional;

@RestController
public class ShelfController {

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private ClosetRepository closetRepository;

    @GetMapping("/library/shelves")
    public List<Shelf> getShelves() {
        return shelfRepository.findAll();
    }

    @GetMapping("/library/shelf/{id}")
    public Shelf getShelf(@PathVariable Integer id) {
        Optional<Shelf> optionalShelf = shelfRepository.findById(id);
        return optionalShelf.orElse(null);
    }

    @PostMapping("/library/shelf")
    public Result addShelf(@RequestBody ShelfDto shelfDto) {
        Optional<Closet> optionalCloset = closetRepository.findClosetById(shelfDto.getClosetId());
        Closet closet = optionalCloset.orElse(null);
        if (closet != null) {
            Shelf shelf = new Shelf();
            shelf.setCloset(closet);
            shelfRepository.save(shelf);
            return new Result("Shelf successfully added", true);
        }
        return new Result("Closet with this id does not exist", false);
    }
}
