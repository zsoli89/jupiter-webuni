package hu.webuni.spring.jupiterwebuni.model;

import hu.webuni.spring.jupiterwebuni.model.course.Course;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Audited
public class TimeTableItem {

    @Id
    @GeneratedValue
    @ToString.Include
    @EqualsAndHashCode.Include
    private int id;

    private int dayOfWeek;
    private LocalTime startLesson;
    private LocalTime endLesson;

    @ManyToOne
    private Course course;
}
