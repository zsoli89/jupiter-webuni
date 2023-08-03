package hu.webuni.spring.jupiterwebuni.model.student;

import hu.webuni.spring.jupiterwebuni.model.UniversityUser;
import hu.webuni.spring.jupiterwebuni.model.course.Course;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.Set;

@Audited
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Data   //getter setter equals hashcode
public class Student extends UniversityUser {

    private Long semester;
    private Integer eduId;
    private Integer numFreeSemesters;
    private int balance;
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;

    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }
}
