package hu.webuni.spring.jupiterwebuni.model.student;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class StudentDto {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private LocalDate birthdate;
    private Long semester;
}
