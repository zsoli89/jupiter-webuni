package hu.webuni.spring.jupiterwebuni.security;

import hu.webuni.spring.jupiterwebuni.model.UniversityUser;
import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacebookLoginService {

    private static final String GRAPH_API_BASE_URL = "https://graph.facebook.com/v13.0";

    private final UserRepository userRepository;

    @Getter
    @Setter
    public static class FacebookData {
        private String email;
        private long id;
    }


    @Transactional
    public UserDetails getUserDetailsForToken(String fbToken) {
        FacebookData fbData = getEmailOfFbUser(fbToken);
        UniversityUser universityUser = findOrCreateUser(fbData);
        return UniversityUserDetailsService.createUserDetails(universityUser);

    }

    private FacebookData getEmailOfFbUser(String fbToken) {
        return WebClient
                .create(GRAPH_API_BASE_URL)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/me") // itt lehet elerni a megfelelo user adatokat
                        .queryParam("fields", "email,name")     // email es name-re van szuksegem
                        .build()
                )
                .headers(headers -> headers.setBearerAuth(fbToken))
                .retrieve()
                .bodyToMono(FacebookData.class)
                .block();
    }


    private UniversityUser findOrCreateUser(FacebookData facebookData) {
        String fbId = String.valueOf(facebookData.getId());
        Optional<UniversityUser> optionalExistingUser = userRepository.findByFacebookId(fbId);
        if(optionalExistingUser.isEmpty()) {
            Student newUser = Student.builder()
                    .facebookId(fbId)
                    .username(facebookData.getEmail())
                    .password("dummy")
                    .build();
            return userRepository.save(newUser);
        } else {
            return optionalExistingUser.get();
        }
    }

}
