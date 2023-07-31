package hu.webuni.spring.jupiterwebuni.model.teacher;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TeacherDto {

    private Long id;
    @NotEmpty
    private String name;
    @NotNull
    private LocalDate birthdate;

}
