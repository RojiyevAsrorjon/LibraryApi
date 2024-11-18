package uz.demo.jpa_library_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.demo.jpa_library_springboot.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {


    Book findBookByNameAndAuthor(String name, String author);
}
