package hu.webuni.spring.jupiterwebuni.repository;


import hu.webuni.spring.jupiterwebuni.model.UniversityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UniversityUser, Integer> {

    Optional<UniversityUser> findByUsername(String username);

    Optional<UniversityUser> findByFacebookId(String fbId);

    Optional<UniversityUser> findByGoogleId(String googleId);
}
