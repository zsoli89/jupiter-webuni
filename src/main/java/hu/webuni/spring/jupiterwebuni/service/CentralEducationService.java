package hu.webuni.spring.jupiterwebuni.service;

import hu.webuni.spring.jupiterwebuni.aspect.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class CentralEducationService {

    private Random random = new Random();

//    @Retry(times = 3, waitTime = 500)
    public int getNumFreeSemestersForStudent(int eduId) {
//        return new StudentXmlWsImplService().getStudentXmlWsImplPort().getNumFreeSemestersForStudent(eduId);
        int rnd = random.nextInt(0,2);
        if (rnd == 0) {
            throw new RuntimeException("Central Education Service timed out.");
        } else {
            return random.nextInt(0, 10);
        }
    }

}
