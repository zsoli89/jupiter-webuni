package hu.webuni.spring.jupiterwebuni.model.student;

import hu.webuni.jupiterwebuni.api.model.StudentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student dtoToEntity(StudentDto dto);
    StudentDto entityToDto(Student entity);
}
