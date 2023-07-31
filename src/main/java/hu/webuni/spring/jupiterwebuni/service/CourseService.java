package hu.webuni.spring.jupiterwebuni.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import hu.webuni.spring.jupiterwebuni.model.course.Course;
import hu.webuni.spring.jupiterwebuni.model.course.QCourse;
import hu.webuni.spring.jupiterwebuni.model.historydata.HistoryData;
import hu.webuni.spring.jupiterwebuni.repository.CourseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditProperty;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class CourseService {

    @PersistenceContext
    private EntityManager em;

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
    @Cacheable("courseSearchResults")
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

    @Transactional
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<HistoryData<Course>> getHistoryById(int id) {

        List resultList = AuditReaderFactory.get(em)
                .createQuery()
                .forRevisionsOfEntity(Course.class, false, true)
                .add(AuditEntity.property("id").eq(id))
                .getResultList().stream().map(o -> {
                    Object[] objArray = (Object[]) o;

                    DefaultRevisionEntity defaultRevisionEntity = (DefaultRevisionEntity) objArray[1];
                    RevisionType revType = (RevisionType) objArray[2];

                    Course course = (Course) objArray[0];
                    course.getStudents().size();
                    course.getTeachers().size();

                    HistoryData<Course> historyData =
                            new HistoryData<>(
                                    course, revType,
                                    defaultRevisionEntity.getId(), defaultRevisionEntity.getRevisionDate());
                    return historyData;
                }).toList();
        return resultList;
    }

    @Transactional
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Course getVersionAt(int id, OffsetDateTime when) {
        long epochMillis = when.toInstant().toEpochMilli();
        AuditProperty<Object> timestampProperty = AuditEntity.revisionProperty("timestamp");
        List resultList = AuditReaderFactory.get(em)
                .createQuery()
                .forRevisionsOfEntity(Course.class, true, false)
                .add(AuditEntity.property("id").eq(id))
                .add(timestampProperty.le(epochMillis))
                .addOrder(timestampProperty.desc())
                .setMaxResults(1)
                .getResultList();

        if(!resultList.isEmpty()) {
            Course course = (Course) resultList.get(0);
            course.getStudents().size();
            course.getTeachers().size();
            return course;
        }

        return null;
    }

}
