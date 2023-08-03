package hu.webuni.spring.jupiterwebuni.service;

import hu.webuni.jupiterwebuni.api.model.StudentDto;
import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.model.student.StudentMapper;
import hu.webuni.spring.jupiterwebuni.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository repository;
    private final StudentMapper mapper;
    private final CentralEducationService centralEducationService;

    @Value("${jupiterwebuni.content.profilePics}")
    private String profilePicsFolder;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Path.of(profilePicsFolder));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

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
                    System.out.println("Free semesters of {%s}, number: {%s}".formatted(student.getName(), numFreeSemesters));
                    repository.save(student);
                }
            } catch (Exception e) {
                log.error("Error calling central education service.", e);
            }
        });
    }

    private Path getProfilePicPathForStudent(Integer id) {
        return Paths.get(profilePicsFolder, id.toString() + ".jpg");
    }

    public Resource getProfilePicture(Integer studentId) {
        FileSystemResource fileSystemResource = new FileSystemResource(getProfilePicPathForStudent(studentId));
        if(!fileSystemResource.exists())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return fileSystemResource;
    }

    public void saveProfilePicture(Integer id, InputStream is) {
        if(!repository.existsById(Long.valueOf(id)))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        try {
            Files.copy(is, getProfilePicPathForStudent(id), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void updateBalance(int studentId, int amount) {
        repository.findById((long) studentId).ifPresent(s -> s.setBalance(s.getBalance() + amount));
    }
}
