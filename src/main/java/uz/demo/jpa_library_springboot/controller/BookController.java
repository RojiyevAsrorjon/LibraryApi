package uz.demo.jpa_library_springboot.controller;

import ch.qos.logback.core.net.server.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.demo.jpa_library_springboot.model.Book;
import uz.demo.jpa_library_springboot.model.Closet;
import uz.demo.jpa_library_springboot.model.Floor;
import uz.demo.jpa_library_springboot.model.Shelf;
import uz.demo.jpa_library_springboot.model.dtos.BookDto;
import uz.demo.jpa_library_springboot.model.dtos._BookDto;
import uz.demo.jpa_library_springboot.repository.BookRepository;
import uz.demo.jpa_library_springboot.repository.ShelfRepository;
import uz.demo.jpa_library_springboot.server.Result;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ShelfRepository shelfRepository;

    @GetMapping("/library/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/library/book/{id}")
    public Book getBook(@PathVariable Integer id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @GetMapping("/library/book-floor/{id}")
    public Floor getFloorByBookId(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return book.getShelf().getCloset().getFloor();
        }
        return null;
    }

    @GetMapping("/library/book-shelf/{id}")
    public Shelf getShelfByBookId(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return book.getShelf();
        }
        return null;
    }

    @GetMapping("/library/book-closet/{id}")
    public Closet getClosetByBookId(@PathVariable Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return book.getShelf().getCloset();
        }
        return null;
    }

    @GetMapping("/library/book-name-author")
    public Book getBookByNameAuthor(@RequestBody _BookDto bookDto) {
        Book book = bookRepository.findBookByNameAndAuthor(bookDto.getName(), bookDto.getAuthor());
        return book;
    }


    @PostMapping("/library/book")
    public Result addBook(@RequestBody BookDto bookDto) {

        Optional<Shelf> optionalShelf = shelfRepository.findById(bookDto.getShelfId());
        Shelf shelf = optionalShelf.orElse(null);
        if (shelf == null) {
            return new Result("Shelf with this ID does not exist", false);
        }
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setName(bookDto.getName());
        book.setShelf(shelf);
        bookRepository.save(book);
        return new Result("Book successfully added", true);
    }

    @DeleteMapping("/library/book-delete/{id}")
    public Result deleteBook(@RequestBody Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            bookRepository.deleteById(id);
            return new Result("Book successfully deleted", true);
        }
        return new Result("Book with this ID does not exist", false);
    }

    @PutMapping("/library/book-edit/{id}")
    public Result updateBook(@RequestBody BookDto bookDto, @PathVariable Integer id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setAuthor(bookDto.getAuthor());
            book.setName(bookDto.getName());
            Optional<Shelf> optionalShelf = shelfRepository.findById(id);
            if (optionalShelf.isPresent()) {
                Shelf shelf = optionalShelf.get();
                book.setShelf(shelf);
                bookRepository.save(book);
                return new Result("Book successfully updated", true);
            } else {
                return new Result("Shelf with this ID does not exist", false);
            }
        }
        return new Result("Book with this ID does not exist", false);
    }
}
