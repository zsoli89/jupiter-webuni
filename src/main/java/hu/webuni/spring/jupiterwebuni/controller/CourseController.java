package hu.webuni.spring.jupiterwebuni.controller;

import com.querydsl.core.types.Predicate;
import hu.webuni.spring.jupiterwebuni.model.course.Course;
import hu.webuni.spring.jupiterwebuni.model.course.CourseDto;
import hu.webuni.spring.jupiterwebuni.model.course.CourseMapper;
import hu.webuni.spring.jupiterwebuni.repository.CourseRepository;
import hu.webuni.spring.jupiterwebuni.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @PostMapping
    public CourseDto createcourse(@RequestBody @Valid CourseDto courseDto) {
        Course course = courseRepository.save(courseMapper.dtoToCourse(courseDto));
        return  courseMapper.courseToDto(course);
    }

//    @GetMapping("/search")
//    public List<CourseDto> searchCourses(
//            @QuerydslPredicate(root = Course.class)Predicate predicate,
//            @RequestParam Optional<Boolean> full) {
//        return courseMapper.courseSummariesToDtos(courseRepository.findAll(predicate));
//    }

    @GetMapping("/search")
    public List<CourseDto> searchCourses(
            @QuerydslPredicate(root = Course.class) Predicate predicate,
            @RequestParam Optional<Boolean> full,
            @SortDefault("id") Pageable pageable) {

        if(full.orElse(false)) {
            return courseMapper.coursesToDtos(courseService.searchCourses(predicate, pageable));
        } else {
            return courseMapper.courseSummariesToDtos(courseRepository.findAll(predicate, pageable));
        }
    }
}
