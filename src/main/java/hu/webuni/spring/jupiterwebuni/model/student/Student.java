package hu.webuni.spring.jupiterwebuni.model.student;

import hu.webuni.spring.jupiterwebuni.model.course.Course;
import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Cacheable
@Data   //getter setter equals hashcode
public class Student {

    @Id
    @GeneratedValue
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
