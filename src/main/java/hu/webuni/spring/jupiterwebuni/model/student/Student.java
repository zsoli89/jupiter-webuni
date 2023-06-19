package hu.webuni.spring.jupiterwebuni.model.student;

import hu.webuni.spring.jupiterwebuni.model.course.Course;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Data   //getter setter eqals hashcode
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include()
    @ToString.Include
    private Long id;
    @ToString.Include
    private String name;
    private LocalDate birthdate;
    private Long semester;
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;
}
