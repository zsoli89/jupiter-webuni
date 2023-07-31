package hu.webuni.spring.jupiterwebuni.model.course;

import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.model.teacher.Teacher;
import lombok.*;

import jakarta.persistence.*;
import org.hibernate.envers.Audited;

import java.util.Set;

@Audited
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@NamedEntityGraph(
        name="Course.students",
        attributeNodes = @NamedAttributeNode("students")
)
@NamedEntityGraph(
        name="Course.teachers",
        attributeNodes = @NamedAttributeNode("teachers")
)
public class Course {

    @Id
    @GeneratedValue
    @ToString.Include
    @EqualsAndHashCode.Include
    private Long id;
    @ToString.Include
    private String name;
    @ManyToMany
    private Set<Student> students;
    @ManyToMany
    private Set<Teacher> teachers;
//    manytomany listtel, ha módosítanánk a listát, minden egyes db-be szinkronkor kitörli az összes meglévőt és újra
//    beszúrja a most aktuálisat
//    ha rendezés kell in-memory, egyébként szinte mindig Set manytomanykor
}
