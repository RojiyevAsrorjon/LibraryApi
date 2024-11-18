package uz.demo.jpa_library_springboot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "closets")
public class Closet {

    @Id
    private String id;

    @ManyToOne
    private Floor floor;
}
