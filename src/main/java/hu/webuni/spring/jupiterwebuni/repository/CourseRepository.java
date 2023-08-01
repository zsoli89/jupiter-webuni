package hu.webuni.spring.jupiterwebuni.repository;

import hu.webuni.spring.jupiterwebuni.model.course.Course;
import hu.webuni.spring.jupiterwebuni.model.course.CourseStat;
import hu.webuni.spring.jupiterwebuni.model.course.QCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends
        JpaRepository<Course, Long>,
        QuerydslPredicateExecutor<Course>,
        QuerydslBinderCustomizer<QCourse>,
        QuerydslWithEntityGraphRepository<Course, Long>{

    @Override
    default void customize(QuerydslBindings bindings, QCourse course) {

        bindings.bind(course.name).first((path, value) -> path.startsWithIgnoreCase(value));
        bindings.bind(course.teachers.any().name).first((path, value) -> path.startsWithIgnoreCase(value));
        bindings.bind(course.students.any().name).first((path, value) -> path.startsWithIgnoreCase(value));

        bindings.bind(course.students.any().semester).all((path, values) -> {
            if(values.size() != 2)
                return Optional.empty();
            Iterator<? extends Long> iterator = values.iterator();
            Long from = iterator.next();
            Long to = iterator.next();

            return Optional.of(path.between(from, to));
        });
    }

//    2. megoldási ötlet
//    nem tud query impl. generálódni
//    @EntityGraph(attributePaths = {"teachers"})
//    Iterable<Course> findAllWithTeachers(Predicate predicate);
//
//    @EntityGraph(attributePaths = {"student"})
//    Iterable<Course> findAllWithStudents(Predicate predicate);


//    1. megoldási ötlet
//    Ha 2-nél több többes kapcsolatot töltök be, akár left join fetch, akár entitygraph -> Descartes
//    mindig betöltődnének a kapcsolatok, nem lehet dinamikusan eldönteni kellenek-e
//    @Override
//    @EntityGraph(attributePaths = {"teachers", "students"})
//    Iterable<Course> findAll(Predicate predicate);

    List<Course> findByName(String name);

    @Query("SELECT c.id as courseId, c.name as courseName, AVG(s.semester) as averageSemesterOfStudents "
            + "FROM Course c LEFT JOIN c.students s "
            + "GROUP BY c")
    List<CourseStat> getCourseStats();
}
