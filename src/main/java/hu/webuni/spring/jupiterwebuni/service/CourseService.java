package hu.webuni.spring.jupiterwebuni.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import hu.webuni.spring.jupiterwebuni.model.course.Course;
import hu.webuni.spring.jupiterwebuni.model.course.QCourse;
import hu.webuni.spring.jupiterwebuni.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@AllArgsConstructor
@Service
public class CourseService {

    private final CourseRepository courseRepository;

    //1. verzió: lapozás nélkül
//	@Transactional
//	public List<Course> searchCourses(Predicate predicate) {
//		List<Course> courses = courseRepository.findAll(predicate, "Course.students");
//		courses = courseRepository.findAll(QCourse.course.in(courses), "Course.teachers");
//		return courses;
//	}

//    lapozás esetén nem tudjuk jól kezelni a joinokat, mivel nem tudja mennyi sor fog bejönni a join miatt, nem tud megadni limitet
//    először lapozásos alapentitásokat megkeresni utána befetchelni a kapcsolatokat - bizonyos adatmennyiség felett érezhető
//    az optimalizálás
    @Transactional
    public List<Course> searchCourses(Predicate predicate, Pageable pageable) {
//      itt még rendezve vannak a kurzusok
        Page<Course> coursePage = courseRepository.findAll(predicate, pageable);
//      adott oldalra eső kurzus id-ket fogja megtalálni
        BooleanExpression courseIdInPredicate = QCourse.course.in(coursePage.getContent());
//      itt már nincs rendezve, mindegy is
        List<Course> courses = courseRepository.findAll(courseIdInPredicate, "Course.students", Sort.unsorted());
//      megint rendezett
        courses = courseRepository.findAll(courseIdInPredicate, "Course.teachers", pageable.getSort());
        return courses;
    }
}
