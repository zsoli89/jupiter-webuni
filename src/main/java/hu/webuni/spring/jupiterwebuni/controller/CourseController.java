package hu.webuni.spring.jupiterwebuni.controller;

import com.querydsl.core.types.Predicate;
import hu.webuni.jupiterwebuni.api.CourseControllerApi;
import hu.webuni.jupiterwebuni.api.model.CourseDto;
import hu.webuni.spring.jupiterwebuni.model.course.Course;
import hu.webuni.spring.jupiterwebuni.model.course.CourseMapper;
import hu.webuni.spring.jupiterwebuni.repository.CourseRepository;
import hu.webuni.spring.jupiterwebuni.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class CourseController implements CourseControllerApi {

    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final NativeWebRequest request;
    private final MethodArgumentResolverHelper resolverHelper;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.of(request);
    }

    @Override
    public ResponseEntity<CourseDto> createcourse(CourseDto courseDto) {
        Course course = courseRepository.save(courseMapper.dtoToCourse(courseDto));
        return ResponseEntity.ok(courseMapper.courseToDto(course));
    }

    public void configurePredicate(@QuerydslPredicate(root = Course.class) Predicate predicate) {}

    public void configPageable(@SortDefault("id") Pageable pageable) {}

    @Override
    public ResponseEntity<List<CourseDto>> searchCourses(Boolean full, Integer page, Integer size, List<String> sort) {
        Pageable pageable = resolverHelper.createPageable(this.getClass(), "configPageable", request);
        Predicate predicate = resolverHelper.createPredicate(this.getClass(), "configurePredicate", request);
        boolean isFull = full == null ? false : full;
        if(isFull) {
            Iterable<Course> courses = courseService.searchCourses(
                    predicate,
                    pageable);
            return ResponseEntity.ok(courseMapper.coursesToDtos(courses));
        } else {
            Iterable<Course> courses = courseRepository.findAll(predicate, pageable);
            return ResponseEntity.ok(courseMapper.courseSummariesToDtos(courses));
        }
    }

}
