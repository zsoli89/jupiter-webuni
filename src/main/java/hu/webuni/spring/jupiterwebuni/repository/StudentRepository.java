package hu.webuni.spring.jupiterwebuni.repository;

import hu.webuni.spring.jupiterwebuni.model.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Student s WHERE s.name=?1 AND s.birthdate=?2")
    boolean isExistByNameAndBirthdate(String name, LocalDate birthdate);

}
