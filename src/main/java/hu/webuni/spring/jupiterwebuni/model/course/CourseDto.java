package hu.webuni.spring.jupiterwebuni.model.course;

import hu.webuni.spring.jupiterwebuni.model.student.StudentDto;
import hu.webuni.spring.jupiterwebuni.model.teacher.TeacherDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private List<StudentDto> students;
    private List<TeacherDto> teachers;
}
