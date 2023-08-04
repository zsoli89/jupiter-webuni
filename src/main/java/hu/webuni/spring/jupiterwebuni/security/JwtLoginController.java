package hu.webuni.spring.jupiterwebuni.security;

import hu.webuni.spring.jupiterwebuni.dtoandfinance.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtLoginController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    FacebookLoginService facebookLoginService;

    @Autowired
    GoogleService googleLoginService;

//    @Autowired
//    GoogleLoginService googleLoginService;

//    @PostMapping("/api/login")
//    public String login(@RequestBody LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
//        return "\"" + jwtService.creatJwtToken((UserDetails) authentication.getPrincipal()) + "\"";
//    }

//    @PostMapping("/api/login")
//    public String login(@RequestBody LoginDto loginDto) {
//        UserDetails userDetails = null;
//        String fbToken = loginDto.getFbToken();
//        if (ObjectUtils.isEmpty(fbToken)) {
//
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
//            userDetails = (UserDetails) authentication.getPrincipal();
//        } else {
//            userDetails = facebookLoginService.getUserDetailsForToken(fbToken);
//        }
//        return "\"" + jwtService.creatJwtToken(userDetails) + "\"";
//    }

    @PostMapping("/api/login")
    public String login(@RequestBody LoginDto loginDto) {
        UserDetails userDetails = null;
        String fbToken = loginDto.getFbToken();
        String googleToken = loginDto.getGoogleToken();
        if(ObjectUtils.isEmpty(fbToken)) {

            if(ObjectUtils.isEmpty(googleToken)) {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
                userDetails = (UserDetails) authentication.getPrincipal();
            } else {
                userDetails = googleLoginService.getUserDetailsForToken(googleToken);
            }

        } else {
            userDetails = facebookLoginService.getUserDetailsForToken(fbToken);
        }
        return "\""+ jwtService.creatJwtToken(userDetails) + "\"";
    }
}
