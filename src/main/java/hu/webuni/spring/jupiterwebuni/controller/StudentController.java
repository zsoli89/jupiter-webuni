package hu.webuni.spring.jupiterwebuni.controller;

import hu.webuni.jupiterwebuni.api.StudentControllerApi;
import hu.webuni.jupiterwebuni.api.model.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/student")
public class StudentController implements StudentControllerApi {

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return StudentControllerApi.super.getRequest();
    }

    @Override
    public ResponseEntity<StudentDto> create1(StudentDto studentDto) {
        return StudentControllerApi.super.create1(studentDto);
    }

    @Override
    public ResponseEntity<Void> delete1(Long id) {
        return StudentControllerApi.super.delete1(id);
    }

    @Override
    public ResponseEntity<List<StudentDto>> findAll1() {
        return StudentControllerApi.super.findAll1();
    }

    @Override
    public ResponseEntity<StudentDto> findById1(Long id) {
        return StudentControllerApi.super.findById1(id);
    }

    @Override
    public ResponseEntity<StudentDto> update1(StudentDto studentDto) {
        return StudentControllerApi.super.update1(studentDto);
    }
}
