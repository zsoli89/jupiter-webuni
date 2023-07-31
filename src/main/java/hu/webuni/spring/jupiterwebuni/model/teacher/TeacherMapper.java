package hu.webuni.spring.jupiterwebuni.model.teacher;

import hu.webuni.jupiterwebuni.api.model.TeacherDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    Teacher dtoToEntity(TeacherDto dto);
    TeacherDto entityToDto(Teacher entity);
}
