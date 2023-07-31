package hu.webuni.spring.jupiterwebuni.service;

import hu.webuni.jupiterwebuni.api.model.StudentDto;
import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.model.student.StudentMapper;
import hu.webuni.spring.jupiterwebuni.repository.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository repository;
    private final StudentMapper mapper;
    private final CentralEducationService centralEducationService;

    public List<StudentDto> findAll() {
        List<StudentDto> studentDtoList = repository.findAll().stream().map(mapper::entityToDto).toList();
        logger.info("Find all student list size: {}", studentDtoList.size());
        return studentDtoList;
    }

    public StudentDto findById(Long id) {
        Optional<Student> entity = repository.findById(id);
        if (entity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        logger.info("Student entity found by id: {}", id);
        return mapper.entityToDto(entity.get());
    }

    @Transactional
    public StudentDto create(StudentDto dto) {
        boolean isExist = repository.isExistStudentByNameAndBirthdate(dto.getName(), dto.getBirthdate());
        if (isExist) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Student savedEntity = repository.save(mapper.dtoToEntity(dto));
        logger.info("Student saved in repository with id {}", savedEntity.getId());
        return mapper.entityToDto(savedEntity);
    }

    @Transactional
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

//    @Scheduled(cron = "${jupiterwebuni.freeSemesterUpdater.cron}")
    public void updateFreeSemesters() {
        List<Student> students = repository.findAll();

        students.forEach(student -> {
            System.out.format("Get number of free semesters of student %s%n", student.getName());

            try {
                Integer eduId = student.getEduId();
                if (eduId != null) {
                    int numFreeSemesters = centralEducationService.getNumFreeSemestersForStudent(eduId);
                    student.setNumFreeSemesters(numFreeSemesters);
                    repository.save(student);
                }
            } catch (Exception e) {
                log.error("Error calling central education service.", e);
            }
        });
    }

}
