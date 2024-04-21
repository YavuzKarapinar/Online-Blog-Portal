package me.jazzy.obp.controller;

import lombok.AllArgsConstructor;
import me.jazzy.obp.dto.*;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.service.AuthService;
import me.jazzy.obp.service.ResetPasswordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;
    private final ResetPasswordService resetPasswordService;

    @PostMapping("/register")
    public ResponseEntity<ResponseBody> login(@RequestBody RegisterDto registerDto) {
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseBody> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseBody> resetPasswordRequest(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return new ResponseEntity<>(resetPasswordService.resetPasswordRequest(resetPasswordRequest), HttpStatus.OK);
    }

    @PostMapping("/checkResetToken")
    public ResponseEntity<ResponseBody> resetPasswordRequest(@RequestParam UUID token) {
        return new ResponseEntity<>(resetPasswordService.checkResetToken(token), HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ResponseBody> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        return new ResponseEntity<>(resetPasswordService.changePassword(changePasswordDto), HttpStatus.OK);
    }
}