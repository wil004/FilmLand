package com.sogeti.filmland.security;

import com.sogeti.filmland.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<Object> signIn(@RequestBody UserDTO userDTO) throws AccountNotFoundException {
        String token = authService.signIn(userDTO);;

        return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .body("Successfully signed in: Token generated :" + token);
    }

    @PostMapping("/signup")
    private ResponseEntity<Object> signUp(@RequestBody UserDTO userDTO) {
        int userId = authService.createUser(userDTO);

        return ResponseEntity.ok()
                .body("Account successfully registered email: " + userDTO.getEmail() + " userId:" + userId);
    }

}