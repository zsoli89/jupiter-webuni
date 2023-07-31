package hu.webuni.spring.jupiterwebuni.service;

import hu.webuni.spring.jupiterwebuni.model.course.Course;
import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.model.teacher.Teacher;
import hu.webuni.spring.jupiterwebuni.repository.CourseRepository;
import hu.webuni.spring.jupiterwebuni.repository.StudentRepository;
import hu.webuni.spring.jupiterwebuni.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Service
public class InitDbService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void deleteDb() {
        studentRepository.deleteAllInBatch();
        teacherRepository.deleteAllInBatch();
        courseRepository.deleteAllInBatch();
    }

    @Transactional
    public void deleteAudTables() {
        jdbcTemplate.update("DELETE FROM teacher_aud");
        jdbcTemplate.update("DELETE FROM course_aud");
        jdbcTemplate.update("DELETE FROM student_aud");
        jdbcTemplate.update("DELETE FROM course_students_aud");
        jdbcTemplate.update("DELETE FROM course_teachers_aud");
        jdbcTemplate.update("DELETE FROM revinfo");
    }

    @Transactional
    public void addInitData() {
        Student student1 = createStudent("Kerekes Ivó", LocalDate.of(1990, 9, 9), 2L, 111);
        Student student2 = createStudent("Sipos Armandó", LocalDate.of(1991, 4, 3), 1L, 222);
        Student student3 = createStudent("Olajos Seherezádé", LocalDate.of(1991, 8, 2), 1L, 333);

        Teacher teacher1 = createTeacher("teacher1", LocalDate.of(2000,10,10));
        Teacher teacher2 = createTeacher("teacher2", LocalDate.of(2000,10,10));
        Teacher teacher3 = createTeacher("teacher3", LocalDate.of(2000,10,10));

        createCourse("course1", Arrays.asList(teacher1, teacher2), Arrays.asList(student1, student2, student3));
        createCourse("course2", Arrays.asList(teacher2), Arrays.asList(student1, student3));
        createCourse("course3", Arrays.asList(teacher1, teacher3), Arrays.asList(student2, student3));
    }

    @Transactional
    public void modifyCourse() {
        Course course1 = courseRepository.findByName("course1").get(0);
        course1.setName("course1_mod");
        System.out.println(course1.getId());
    }

    private Course createCourse(String name, List<Teacher> teachers, List<Student> students) {
        return courseRepository.save(
                Course.builder()
                        .name(name)
                        .teachers(new HashSet<>(teachers))
                        .students(new HashSet<>(students))
                        .build());
    }

    private Teacher createTeacher(String name, LocalDate birthDate) {
        return teacherRepository.save(
                Teacher.builder()
                        .name(name)
                        .birthdate(birthDate)
                        .build());
    }

    private Student createStudent(String name, LocalDate birthdate, Long semester, Integer eduId) {
        return studentRepository.save(
                Student.builder()
                        .name(name)
                        .birthdate(birthdate)
                        .semester(semester)
                        .eduId(eduId)
                        .build());
    }

}
