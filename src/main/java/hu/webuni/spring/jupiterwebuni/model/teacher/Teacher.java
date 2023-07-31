package hu.webuni.spring.jupiterwebuni.model.teacher;

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
@Cacheable
@Entity
public class Teacher {
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include()
    @ToString.Include
    private Long id;
    private String name;
    private LocalDate birthdate;
    @ManyToMany(mappedBy = "teachers")
    private Set<Course> courses;

}
