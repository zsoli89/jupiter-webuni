package hu.webuni.spring.jupiterwebuni.service;

import hu.webuni.eduservice.wsclient.StudentXmlWsImplService;
import hu.webuni.spring.jupiterwebuni.aspect.Retry;
import jakarta.jms.Topic;
import jmsdto.FreeSemesterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class CentralEducationService {

    private final Random random = new Random();
    private final JmsTemplate educationTemplate;

//    @Retry(times = 3, waitTime = 500)
//    public int getNumFreeSemestersForStudent(int eduId) {
//        ha legeneraltam a pom-ban levo jaxws-es plugin altal az osztalyokat utana tudom meghivni
//        futnia kell a masik ms-nek hogy le tudjam generalni a fajlokat
//        ezt a scheduled metodusban hivogattuk
//        ez a webservices resz
//        jms-hez van a university-finance
//        return new StudentXmlWsImplService().getStudentXmlWsImplPort().getNumFreeSemestersForStudent(eduId);
//        int rnd = random.nextInt(0,2);
//        if (rnd == 0) {
//            throw new RuntimeException("Central Education Service timed out.");
//        } else {
//            return random.nextInt(0, 10);
//        }
//    }


    public static final String FREE_SEMESTER_RESPONSES = "free_semester_responses";
//    private final JmsTemplate educationTemplate;

    @Retry(times = 5, waitTime = 500)
    public int getNumFreeSemestersForStudent(int eduId) {
        return new StudentXmlWsImplService().getStudentXmlWsImplPort().getNumFreeSemestersForStudent(eduId);

    }

//    o elkuld egy uzenetet nem jon egybol valasz, ez csak egy void
    public void askNumFreeSemestersForStudent(int eduId) {
//  o egy bizonyos queue-ba kuld uzenetet viszont a headerbe be szeretnem allitani melyik az a topic amire szeretnem az uzenetet kapni
//        ez egy ideiglenes topic lesz, sessionnel letre kell hozni
        Topic responseTopic = educationTemplate.execute(session ->
                session.createTopic(FREE_SEMESTER_RESPONSES));

        FreeSemesterRequest freeSemesterRequest = new FreeSemesterRequest();
        freeSemesterRequest.setStudentId(eduId);
//        a freeSemesterRequest a body, a header a lambda
//        a free_semester_requestre iratkozott fel masik oldalon - education-service FreeSemesterReequestConsumer osztályban
        educationTemplate.convertAndSend("free_semester_requests", freeSemesterRequest, message -> {
            message.setJMSReplyTo(responseTopic);
            return message;
        });
    }
}
