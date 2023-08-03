package hu.webuni.spring.jupiterwebuni.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

@Entity
@Audited
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UniversityUser {

    public enum UserType {
        TEACHER, STUDENT;
    }

    @Id
    @GeneratedValue
    @ToString.Include
    @EqualsAndHashCode.Include
    private int id;

    @ToString.Include
    private String name;

    private LocalDate birthdate;

    private String username;
    private String password;
    private String facebookId;
    private String googleId;

    public abstract UserType getUserType();
}
