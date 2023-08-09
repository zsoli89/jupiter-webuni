package hu.webuni.spring.jupiterwebuni.controller;

import hu.webuni.jupiterwebuni.api.model.StudentDto;
import hu.webuni.spring.jupiterwebuni.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/student")
public class StudentControllerOld {

    private final StudentService service;

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/findall")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDto> findAll() {
        return service.findAll();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto create(@Valid @RequestBody StudentDto dto) {
        return service.create(dto);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public StudentDto update(@RequestBody StudentDto dto) {
        return service.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
