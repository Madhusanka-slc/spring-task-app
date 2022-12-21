package lk.ijse.dep9.app.entity;

import lombok.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements SuperEntity{
    private int id;
    private String name;
    private String username;

    public Project(String name, String username) {
        this.name = name;
        this.username = username;
    }
}
