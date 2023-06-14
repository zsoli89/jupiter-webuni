package hu.webuni.spring.jupiterwebuni.model.teacher;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDto {

    private Long id;
    private String name;
    private LocalDate birthdate;

}
