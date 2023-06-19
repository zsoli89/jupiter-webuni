package hu.webuni.spring.jupiterwebuni.controller;

import hu.webuni.spring.jupiterwebuni.model.student.StudentDto;
import hu.webuni.spring.jupiterwebuni.model.teacher.Teacher;
import hu.webuni.spring.jupiterwebuni.model.teacher.TeacherDto;
import hu.webuni.spring.jupiterwebuni.service.TeacherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    private final TeacherService service;

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/findall")
    @ResponseStatus(HttpStatus.OK)
    public List<TeacherDto> findAll() {
        return service.findAll();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDto create(@Valid @RequestBody TeacherDto dto) {
        return service.create(dto);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public TeacherDto update(@RequestBody TeacherDto dto) {
        return service.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
