package Example.example.entity;

import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Project {

    private Long id;

    private String name;

    private String description;

}
