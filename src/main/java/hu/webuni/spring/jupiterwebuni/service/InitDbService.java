package hu.webuni.spring.jupiterwebuni.service;

import hu.webuni.spring.jupiterwebuni.model.Semester;
import hu.webuni.spring.jupiterwebuni.model.SpecialDay;
import hu.webuni.spring.jupiterwebuni.model.TimeTableItem;
import hu.webuni.spring.jupiterwebuni.model.course.Course;
import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.model.teacher.Teacher;
import hu.webuni.spring.jupiterwebuni.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private final TimeTableItemRepository timeTableItemRepository;
    private final SpecialDayRepository specialDayRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void deleteDb() {
        specialDayRepository.deleteAllInBatch();
        timeTableItemRepository.deleteAllInBatch();
        courseRepository.deleteAllInBatch();
        studentRepository.deleteAllInBatch();
        teacherRepository.deleteAllInBatch();
    }

    @Transactional
    public void deleteAudTables() {
        jdbcTemplate.update("DELETE FROM time_table_item_aud");
        jdbcTemplate.update("DELETE FROM special_day_aud");
        jdbcTemplate.update("DELETE FROM teacher_aud");
        jdbcTemplate.update("DELETE FROM course_aud");
        jdbcTemplate.update("DELETE FROM student_aud");

        jdbcTemplate.update("DELETE FROM course_students_aud");
        jdbcTemplate.update("DELETE FROM course_teachers_aud");
        jdbcTemplate.update("DELETE FROM university_user_aud");
        jdbcTemplate.update("DELETE FROM revinfo");
    }

    @Transactional
    public void addInitData() {
//        Student student1 = createStudent("Kerekes Ivó", LocalDate.of(1990, 9, 9), 2L, 111);
//        Student student2 = createStudent("Sipos Armandó", LocalDate.of(1991, 4, 3), 1L, 222);
//        Student student3 = createStudent("Olajos Seherezádé", LocalDate.of(1991, 8, 2), 1L, 333);

//        Teacher teacher1 = createTeacher("teacher1", LocalDate.of(2000,10,10));
//        Teacher teacher2 = createTeacher("teacher2", LocalDate.of(2000,10,10));
//        Teacher teacher3 = createTeacher("teacher3", LocalDate.of(2000,10,10));

//        Course course1 = createCourse("course1", Arrays.asList(teacher1, teacher2), Arrays.asList(student1, student2, student3));
//        Course course2 = createCourse("course2", Arrays.asList(teacher2), Arrays.asList(student1, student3));
//        Course course3 = createCourse("course3", Arrays.asList(teacher1, teacher3), Arrays.asList(student2, student3));

        Student student1 = saveNewStudent("Kerekes Ivó", LocalDate.of(2000, 10, 10), 1, 111, "student1", "pass");
        Student student2 = saveNewStudent("Sipos Armandó", LocalDate.of(2000, 10, 10), 2, 222, "student2", "pass");
        Student student3 = saveNewStudent("Olajos Seherezádé", LocalDate.of(2000, 10, 10), 3, 333, "student3", "pass");

        Teacher teacher1 = saveNewTeacher("teacher1", LocalDate.of(2000, 10, 10), "teacher1", "pass");
        Teacher teacher2 = saveNewTeacher("teacher2", LocalDate.of(2000, 10, 10), "teacher2", "pass");
        Teacher teacher3 = saveNewTeacher("teacher3", LocalDate.of(2000, 10, 10), "teacher3", "pass");

        Course course1 = createCourse("course1", Arrays.asList(teacher1, teacher2), Arrays.asList(student1, student2, student3), 2022, Semester.SemesterType.SPRING);
        Course course2 = createCourse("course2", Arrays.asList(teacher2), Arrays.asList(student1, student3), 2022, Semester.SemesterType.SPRING);
        Course course3 = createCourse("course3", Arrays.asList(teacher1, teacher3), Arrays.asList(student2, student3), 2022, Semester.SemesterType.SPRING);

        addNewTimeTableItem(course1, 1, "10:15", "11:45");
        addNewTimeTableItem(course1, 3, "10:15", "11:45");
        addNewTimeTableItem(course2, 2, "12:15", "13:45");
        addNewTimeTableItem(course2, 4, "10:15", "11:45");
        addNewTimeTableItem(course3, 3, "08:15", "09:45");
        addNewTimeTableItem(course3, 5, "08:15", "09:45");

        saveSpecialDay("2022-04-18", null);
        saveSpecialDay("2022-03-15", null);
        saveSpecialDay("2022-03-14", "2022-03-26");

        System.out.format("Student ids: %d, %d, %d%n", student1.getId(), student2.getId(), student3.getId());
    }

    @Transactional
    public void modifyCourse() {
        Course course1 = courseRepository.findByName("course1").get(0);
        course1.setName("course1_mod");
        System.out.println(course1.getId());
    }

    private Course createCourse(String name, List<Teacher> teachers, List<Student> students, int year, Semester.SemesterType semesterType) {
        return courseRepository.save(
                Course.builder()
                        .name(name)
                        .teachers(new HashSet<>(teachers))
                        .students(new HashSet<>(students))
                        .semester(
                                Semester.builder()
                                        .year(year)
                                        .semesterType(semesterType)
                                        .build())
                        .build());
    }

//    private Course createCourse(String name, List<Teacher> teachers, List<Student> students) {
//        return courseRepository.save(
//                Course.builder()
//                        .name(name)
//                        .teachers(new HashSet<>(teachers))
//                        .students(new HashSet<>(students))
//                        .build());
//    }

    private Teacher createTeacher(String name, LocalDate birthDate) {
        return teacherRepository.save(
                Teacher.builder()
                        .build());
    }

    private Student createStudent(String name, LocalDate birthdate, Long semester, Integer eduId) {
        return studentRepository.save(
                Student.builder()
                        .semester(semester)
                        .eduId(eduId)
                        .build());
    }

    private void addNewTimeTableItem(Course course, int dayOfWeek, String startLession, String endLession) {
        course.addTimeTableItem(timeTableItemRepository.save(
                TimeTableItem.builder()
                        .dayOfWeek(dayOfWeek)
                        .startLesson(LocalTime.parse(startLession))
                        .endLesson(LocalTime.parse(endLession))
                        .build()
        ));
    }

    private void saveSpecialDay(String sourceDay, String targetDay) {
        specialDayRepository.save(
                SpecialDay.builder()
                        .sourceDay(LocalDate.parse(sourceDay))
                        .targetDay(targetDay == null ? null : LocalDate.parse(targetDay))
                        .build());

    }

    private Student saveNewStudent(String name, LocalDate birthdate, int semester, int eduId, String username, String pass) {
        return studentRepository.save(
                Student.builder()
                        .name(name)
                        .birthdate(birthdate)
                        .semester((long) semester)
                        .eduId(eduId)
                        .username(username)
                        .password(passwordEncoder.encode(pass))
                        .build());
    }

    private Teacher saveNewTeacher(String name, LocalDate birthdate, String username, String pass) {
        return teacherRepository.save(
                Teacher.builder()
                        .name(name)
                        .birthdate(birthdate)
                        .username(username)
                        .password(passwordEncoder.encode(pass))
                        .build());
    }

}
