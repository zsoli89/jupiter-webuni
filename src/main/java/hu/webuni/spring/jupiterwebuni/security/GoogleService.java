package hu.webuni.spring.jupiterwebuni.security;

import hu.webuni.spring.jupiterwebuni.model.UniversityUser;
import hu.webuni.spring.jupiterwebuni.model.student.Student;
import hu.webuni.spring.jupiterwebuni.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GoogleService {

    private static final String GOOGLE_BASE_URI = "https://oauth2.googleapis.com";

    private final UserRepository userRepository;

    @Value("${university.google-client-id}")
    private String googleClientId;

    @Getter
    @Setter
    public static class GoogleData {
        private String sub;
        private String email;

        private String aud;

        private String iss;
        private String azp;
        private String email_verified;
        private String at_hash;
        private String name;
        private String picture;
        private String given_name;
        private String family_name;
        private String locale;
        private String iat;
        private String exp;
        private String jti;
        private String alg;
        private String kid;
        private String typ;
    }

    @Transactional
    public UserDetails getUserDetailsForToken(String googleToken) {
        GoogleData googleData = getGoogleDataForToken(googleToken);
//        ez egy tamadasi modszer, valaki beregisztral sajat alkalmazast, szerez ott egy tokent, es ezt a tokent kuldi felenk, es
//          ezt kuldjuk a google fele akkor visszaadna ha nem szurnenk ki, tehat nalunk kell szereznie a tokent
        if(!this.googleClientId.equals(googleData.getAud()))
            throw new BadCredentialsException("The aud claim of the google id token does not match the client id.");

        UniversityUser universityUser = findOrCreateUser(googleData);
        return UniversityUserDetailsService.createUserDetails(universityUser);
    }

    private UniversityUser findOrCreateUser(GoogleData googleData) {
        String googleId = String.valueOf(googleData.getSub());
        Optional<UniversityUser> optionalExistingUser = userRepository.findByGoogleId(googleId);
        if(optionalExistingUser.isEmpty()) {
            Student newUser = Student.builder()
                    .googleId(googleId)
                    .username(googleData.getEmail())
                    .password("dummy")
                    .build();
            return userRepository.save(newUser);
        } else {
            return optionalExistingUser.get();
        }
    }

    private GoogleData getGoogleDataForToken(String googleToken) {
        return WebClient.create(GOOGLE_BASE_URI)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/tokeninfo")
                        .queryParam("id_token", googleToken)
                        .build())
                .retrieve()
                .bodyToMono(GoogleData.class)
                .block();
    }

}
