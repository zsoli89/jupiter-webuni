package hu.webuni.spring.jupiterwebuni.dtoandfinance;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
    private String fbToken;
    private String googleToken;
}
