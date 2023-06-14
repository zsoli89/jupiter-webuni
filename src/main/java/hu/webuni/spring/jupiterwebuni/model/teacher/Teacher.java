package hu.webuni.spring.jupiterwebuni.model.teacher;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Teacher {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    private Long id;
    private String name;
    private LocalDate birthdate;

}
