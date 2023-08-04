package hu.webuni.spring.jupiterwebuni.xmlws;

import hu.webuni.jupiterwebuni.api.model.TimeTableItemDto;
import jakarta.jws.WebService;

import java.time.LocalDate;
import java.util.List;

@WebService
public interface TimeTableWs {

    List<TimeTableItemDto> getTimetableForStudent(Integer studentId, LocalDate from, LocalDate until);

}
