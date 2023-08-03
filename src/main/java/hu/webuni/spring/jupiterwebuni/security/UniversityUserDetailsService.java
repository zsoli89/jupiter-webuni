package hu.webuni.spring.jupiterwebuni.security;

import hu.webuni.spring.jupiterwebuni.model.UniversityUser;
import hu.webuni.spring.jupiterwebuni.model.course.Course;
import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.model.teacher.Teacher;
import hu.webuni.spring.jupiterwebuni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UniversityUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Transactional  // ez a course-teacher/student kapcsolatok miatt kell, lazy init exception miatt
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UniversityUser universityUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return createUserDetails(universityUser);
    }

    public static UserDetails createUserDetails(UniversityUser universityUser) {
        Set<Course> courses = null;
        if (universityUser instanceof Teacher) {
            courses = ((Teacher) universityUser).getCourses();
        } else if (universityUser instanceof Student) {
            courses = ((Student) universityUser).getCourses();
        }
        return new UserInfo(universityUser.getUsername(), universityUser.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(universityUser.getUserType().toString())),
                courses == null ? Collections.emptyList() :
                        courses.stream().map(Course::getId).toList());
    }
}
