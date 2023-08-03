package hu.webuni.spring.jupiterwebuni.model.student;

import hu.webuni.spring.jupiterwebuni.model.course.Course;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.Set;

@Audited
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
    private Integer eduId;
    private Integer numFreeSemesters;
    private int balance;
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;
}
