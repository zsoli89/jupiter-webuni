package hu.webuni.spring.jupiterwebuni.service;

import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class InitDbService {

    private final StudentRepository studentRepository;

    @Transactional
    public void addInitData() {
        studentRepository.save(Student.builder()
                .birthdate(LocalDate.of(1990, 9, 9))
                .name("Kerekes Ivó")
                .semester(2L)
                .build());
        studentRepository.save(Student.builder()
                .birthdate(LocalDate.of(1991, 4, 3))
                .name("Sipos Armandó")
                .semester(1L)
                .build());
        studentRepository.save(Student.builder()
                .birthdate(LocalDate.of(1991, 8, 2))
                .name("Olajos Seherezádé")
                .semester(1L)
                .build());
    }

}
