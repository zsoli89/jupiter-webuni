package hu.webuni.spring.jupiterwebuni.jms;

import hu.webuni.spring.jupiterwebuni.service.CentralEducationService;
import hu.webuni.spring.jupiterwebuni.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FreeSemesterResponseConsumer {

    private final StudentService studentService;

//    @JmsListener(destination = CentralEducationService.FREE_SEMESTER_RESPONSES,
//            containerFactory = "educationFactory")
//    public void onFreeSemesterResponse(FreeSemesterResponse response) {
//        studentService.updateFreeSemesters(response.getStudentId(), response.getNumFreeSemesters());
//    }
}
