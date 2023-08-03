package hu.webuni.spring.jupiterwebuni.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserInfo extends User {

    private List<Long> courseIds;

    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities, List<Long> courseIds) {
        super(username, password, authorities);
        this.courseIds = courseIds;
    }

}
