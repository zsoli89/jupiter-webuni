package hu.webuni.spring.jupiterwebuni.xmlws;

import hu.webuni.jupiterwebuni.api.model.TimeTableItemDto;
import hu.webuni.spring.jupiterwebuni.model.TimeTableItem;
import hu.webuni.spring.jupiterwebuni.model.TimeTableMapper;
import hu.webuni.spring.jupiterwebuni.service.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TimetableWsImpl implements TimeTableWs {

    private final TimeTableService timeTableService;
    private final TimeTableMapper timeTableMapper;

//    a LocalDate miatt kell ide a package-info
    @Override
    public List<TimeTableItemDto> getTimetableForStudent(Integer studentId, LocalDate from, LocalDate until) {
        try {
            if (studentId != null) {
                ArrayList<TimeTableItemDto> result = new ArrayList<>();

                Map<LocalDate, List<TimeTableItem>> timeTableForStudent = timeTableService
                        .getTimeTableForStudent(studentId, from, until);

                for (Map.Entry<LocalDate, List<TimeTableItem>> entry : timeTableForStudent.entrySet()) {
                    LocalDate day = entry.getKey();
                    List<TimeTableItem> items = entry.getValue();
                    List<TimeTableItemDto> itemDtos = timeTableMapper.timeTableItemsToDtos(items);
                    itemDtos.forEach(i -> i.setDay(day));
                    result.addAll(itemDtos);
                }
                return result;
            }

            else
                return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }

    }

}
