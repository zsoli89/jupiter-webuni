package hu.webuni.spring.jupiterwebuni.model.teacher;

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
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
public class Teacher extends UniversityUser {

    @ManyToMany(mappedBy = "teachers")
    private Set<Course> courses;

    @Override
    public UserType getUserType() {
        return UserType.TEACHER;
    }
}
