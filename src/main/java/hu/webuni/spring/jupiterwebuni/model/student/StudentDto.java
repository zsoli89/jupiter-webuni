package hu.webuni.spring.jupiterwebuni.model.student;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class StudentDto {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private LocalDate birthdate;
    private Long semester;
}
