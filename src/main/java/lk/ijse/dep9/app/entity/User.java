package lk.ijse.dep9.app.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


@Data @AllArgsConstructor @NoArgsConstructor
public class User implements SuperEntity{

    private String username;
    private String password;
    private String fullName;



}
