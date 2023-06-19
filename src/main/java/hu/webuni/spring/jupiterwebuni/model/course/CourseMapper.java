package hu.webuni.spring.jupiterwebuni.model.course;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto courseToDto(Course entity);

    @Named("summary")
    @Mapping(ignore = true, target = "teachers")
    @Mapping(ignore = true, target = "students")
    CourseDto courseSummaryToDto(Course entity);
    Course dtoToCourse(CourseDto dto);
    List<CourseDto> coursesToDtos(Iterable<Course> courses);

    @IterableMapping(qualifiedByName = "summary")
    List<CourseDto> courseSummariesToDtos(Iterable<Course> courses);
}
