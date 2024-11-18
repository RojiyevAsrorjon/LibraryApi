package uz.demo.jpa_library_springboot.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Result {
    private String message;
    private boolean success;
}
