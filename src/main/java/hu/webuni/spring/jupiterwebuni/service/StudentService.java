package hu.webuni.spring.jupiterwebuni.service;

import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.model.student.StudentDto;
import hu.webuni.spring.jupiterwebuni.model.student.StudentMapper;
import hu.webuni.spring.jupiterwebuni.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository repository;
    private final StudentMapper mapper;

    public StudentService(
            StudentRepository repository, StudentMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<StudentDto> findAll() {
        List<StudentDto> studentDtoList = repository.findAll().stream().map(mapper::entityToDto).toList();
        logger.info("Find all student list size: %s".formatted(studentDtoList.size()));
        return studentDtoList;
    }

    public StudentDto findById(Long id) {
        Optional<Student> entity = repository.findById(id);
        if (entity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        logger.info("Student entity found by id: {}", id);
        return mapper.entityToDto(entity.get());
    }

    public StudentDto create(StudentDto dto) {
        boolean isExist = repository.isExistByNameAndBirthdate(dto.getName(), dto.getBirthdate());
        if (isExist) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Student savedEntity = repository.save(mapper.dtoToEntity(dto));
        logger.info("Student saved in repository with id {}", savedEntity.getId());
        return mapper.entityToDto(savedEntity);
    }

    public StudentDto update(StudentDto dto) {
        Optional<Student> entity = repository.findById(dto.getId());
        if (entity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Student savedEntity = repository.save(mapper.dtoToEntity(dto));
        logger.info("Student updated in repository with id {}", savedEntity.getId());
        return mapper.entityToDto(savedEntity);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Student> entity = repository.findById(id);
        if (entity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        repository.delete(entity.get());
        logger.info("Student deleted from repository by id {}", id);
    }

}
