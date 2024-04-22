package me.jazzy.obp.service;

import lombok.AllArgsConstructor;
import me.jazzy.obp.config.exception.notfound.UserNotFoundException;
import me.jazzy.obp.dto.LoginDto;
import me.jazzy.obp.dto.RegisterDto;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.model.Role;
import me.jazzy.obp.model.User;
import me.jazzy.obp.security.jwt.JwtGenerator;
import me.jazzy.obp.validator.EmailValidation;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuthService {

    private final EmailValidation emailValidation;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;

    public ResponseBody register(RegisterDto registerDto) {

        boolean isValidEmail = emailValidation.test(registerDto.getEmail());

        if(!isValidEmail)
            throw new UserNotFoundException("Email is not valid.");

        User user = new User(
                registerDto.getFirstName(),
                registerDto.getLastName(),
                registerDto.getEmail(),
                registerDto.getPassword(),
                Role.USER
        );

        userService.saveUser(user);

        return new ResponseBody(
                HttpStatus.CREATED.value(),
                "User created successfully.",
                LocalDateTime.now()
        );
    }

    public ResponseBody login(LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(loginDto.getEmail());

        return new ResponseBody(
                HttpStatus.CREATED.value(),
                token,
                LocalDateTime.now()
        );
    }
}