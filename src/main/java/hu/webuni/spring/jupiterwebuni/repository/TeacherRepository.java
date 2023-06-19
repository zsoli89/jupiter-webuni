package hu.webuni.spring.jupiterwebuni.repository;

import hu.webuni.spring.jupiterwebuni.model.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END FROM Teacher t WHERE t.name=?1 AND t.birthdate=?2")
    boolean isExistTeacherByNameAndBirthdate(String name, LocalDate birthdate);

}
