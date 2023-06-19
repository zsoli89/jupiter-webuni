package hu.webuni.spring.jupiterwebuni.service;

import hu.webuni.spring.jupiterwebuni.model.course.Course;
import hu.webuni.spring.jupiterwebuni.model.course.CourseDto;
import hu.webuni.spring.jupiterwebuni.model.course.CourseMapper;
import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.model.student.StudentDto;
import hu.webuni.spring.jupiterwebuni.model.student.StudentMapper;
import hu.webuni.spring.jupiterwebuni.model.teacher.Teacher;
import hu.webuni.spring.jupiterwebuni.model.teacher.TeacherDto;
import hu.webuni.spring.jupiterwebuni.model.teacher.TeacherMapper;
import hu.webuni.spring.jupiterwebuni.repository.CourseRepository;
import hu.webuni.spring.jupiterwebuni.repository.StudentRepository;
import hu.webuni.spring.jupiterwebuni.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class InitDbService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public void deleteDb() {
        studentRepository.deleteAll();
        teacherRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Transactional
    public void addInitData() {
        Student student1 = studentRepository.save(Student.builder()
                .birthdate(LocalDate.of(1990, 9, 9))
                .name("Kerekes Ivó")
                .semester(2L)
                .build());
        Student student2 = studentRepository.save(Student.builder()
                .birthdate(LocalDate.of(1991, 4, 3))
                .name("Sipos Armandó")
                .semester(1L)
                .build());
        Student student3 = studentRepository.save(Student.builder()
                .birthdate(LocalDate.of(1991, 8, 2))
                .name("Olajos Seherezádé")
                .semester(1L)
                .build());

        Teacher teacher1 = createTeacher("teacher1", LocalDate.of(2000,10,10));
        Teacher teacher2 = createTeacher("teacher2", LocalDate.of(2000,10,10));
        Teacher teacher3 = createTeacher("teacher3", LocalDate.of(2000,10,10));

        createCourse("course1", Arrays.asList(teacher1, teacher2), Arrays.asList(student1, student2, student3));
        createCourse("course2", Arrays.asList(teacher2), Arrays.asList(student1, student3));
        createCourse("course3", Arrays.asList(teacher1, teacher3), Arrays.asList(student2, student3));
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

    private Student createStudent(String name, LocalDate birthdate, Long semester) {
        return studentRepository.save(
                Student.builder()
                        .name(name)
                        .birthdate(birthdate)
                        .semester(semester)
                        .build());
    }

}
