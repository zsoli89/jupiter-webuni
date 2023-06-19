package hu.webuni.spring.jupiterwebuni.service;

import hu.webuni.spring.jupiterwebuni.model.teacher.Teacher;
import hu.webuni.spring.jupiterwebuni.model.teacher.TeacherDto;
import hu.webuni.spring.jupiterwebuni.model.teacher.TeacherMapper;
import hu.webuni.spring.jupiterwebuni.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TeacherService {

    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);
    private final TeacherRepository repository;
    private final TeacherMapper mapper;

    public List<TeacherDto> findAll() {
        List<TeacherDto> teacherDtoList = repository.findAll().stream().map(mapper::entityToDto).toList();
        logger.info("Find all teachers list size: {}", teacherDtoList.size());
        return teacherDtoList;
    }

    public TeacherDto findById(Long id) {
        Optional<Teacher> entity = repository.findById(id);
        if (entity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        logger.info("Teacher entity found by id: {}", id);
        return mapper.entityToDto(entity.get());
    }

    @Transactional
    public TeacherDto create(TeacherDto dto) {
        boolean isExist = repository.isExistTeacherByNameAndBirthdate(dto.getName(), dto.getBirthdate());
        if (isExist) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        Teacher savedEntity = repository.save(mapper.dtoToEntity(dto));
        logger.info("Teacher saved in repository with id {}", savedEntity.getId());
        return mapper.entityToDto(savedEntity);
    }

    @Transactional
    public TeacherDto update(TeacherDto dto) {
        Optional<Teacher> entity = repository.findById(dto.getId());
        if (entity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Teacher savedEntity = repository.save(mapper.dtoToEntity(dto));
        logger.info("Teacher updated in repository with id {}", savedEntity.getId());
        return mapper.entityToDto(savedEntity);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Teacher> entity = repository.findById(id);
        if (entity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        repository.delete(entity.get());
        logger.info("Teacher deleted from repository by id {}", id);
    }

}
