package hu.webuni.spring.jupiterwebuni.ws;

import hu.webuni.spring.jupiterwebuni.security.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CourseChatGuard {

    public boolean checkCourseId(Authentication authentication, Long courseId) {
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        return userInfo.getCourseIds().contains(courseId);
    }
}
