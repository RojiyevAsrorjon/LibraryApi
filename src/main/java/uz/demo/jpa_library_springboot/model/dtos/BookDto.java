package uz.demo.jpa_library_springboot.model.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    String name;
    String author;
    Integer shelfId;
}
