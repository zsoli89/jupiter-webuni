package hu.webuni.spring.jupiterwebuni.model.course;

import hu.webuni.jupiterwebuni.api.model.CourseDto;
import hu.webuni.jupiterwebuni.api.model.HistoryDataCourseDto;
import hu.webuni.spring.jupiterwebuni.model.historydata.HistoryData;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
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

    List<HistoryDataCourseDto> coursesHistoryToHistoryDataCourseDtos(List<HistoryData<Course>> history);

    default OffsetDateTime dateToOffsetDateTime(Date date) {
        return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.of("Z"));
    }
}
