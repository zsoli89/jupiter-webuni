package hu.webuni.spring.jupiterwebuni.controller;

import hu.webuni.jupiterwebuni.api.StudentControllerApi;
import hu.webuni.jupiterwebuni.api.model.StudentDto;
import hu.webuni.spring.jupiterwebuni.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentControllerApi {

    private final StudentService studentService;

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
        return ResponseEntity.ok(studentService.findAll());
    }

    @Override
    public ResponseEntity<StudentDto> findById1(Long id) {
        return StudentControllerApi.super.findById1(id);
    }

    @Override
    public ResponseEntity<Resource> getProfilePicture(Integer id) {
        return ResponseEntity.ok(studentService.getProfilePicture(id));
    }

    @Override
    public ResponseEntity<Void> uploadProfilePicture(Integer id, MultipartFile content) {
        try {
            studentService.saveProfilePicture(id, content.getInputStream());
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<StudentDto> update1(StudentDto studentDto) {
        return StudentControllerApi.super.update1(studentDto);
    }
}
