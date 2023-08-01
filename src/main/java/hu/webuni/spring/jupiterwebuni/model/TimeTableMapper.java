package hu.webuni.spring.jupiterwebuni.model;

import hu.webuni.jupiterwebuni.api.model.TimeTableItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TimeTableMapper {

    @Mapping(target = "courseName", source="course.name")
    public TimeTableItemDto timeTableItemToDto(TimeTableItem item);
//
    public List<TimeTableItemDto> timeTableItemsToDtos(List<TimeTableItem> items);
}
