package me.jazzy.obp.service;

import lombok.AllArgsConstructor;
import me.jazzy.obp.dto.ChangePasswordDto;
import me.jazzy.obp.dto.ResetPasswordRequest;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.model.ResetPassword;
import me.jazzy.obp.model.User;
import me.jazzy.obp.repository.ResetPasswordRepository;
import me.jazzy.obp.validator.EmailValidation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ResetPasswordService {

    private final EmailValidation emailValidation;
    private final EmailSenderService emailSenderService;
    private final ResetPasswordRepository resetPasswordRepository;
    private final UserService userService;

    public ResponseBody resetPasswordRequest(ResetPasswordRequest resetPasswordRequest) {
        boolean isValidEmail = emailValidation.test(resetPasswordRequest.getEmail());

        if (!isValidEmail)
            throw new RuntimeException("There is no such email.");

        UUID uuid = UUID.randomUUID();
        User user = userService.getByEmail(resetPasswordRequest.getEmail());

        ResetPassword resetPassword = new ResetPassword(
                uuid,
                user,
                false,
                LocalDateTime.now().plusMinutes(15)
        );

        resetPasswordRepository.save(resetPassword);

        emailSenderService.sendEmailTo(
                resetPasswordRequest.getEmail(),
                "Password Reset Request",
                "We heard that you want to change your password here is your reset password token: " +
                        "\n http://localhost:8080/checkResetToken?token=" + uuid
        );

        return new ResponseBody(
                HttpStatus.OK.value(),
                "Mail sent to " + resetPasswordRequest.getEmail(),
                LocalDateTime.now()
        );
    }

    public ResponseBody checkResetToken(UUID token) {

        ResetPassword resetPassword = resetPasswordRepository.findByUuid(token)
                .orElseThrow(() -> new RuntimeException("There is no such token"));

        if (isEnabled(resetPassword) || isExpired(resetPassword))
            throw new RuntimeException("Your token is not valid.");

        resetPassword.setEnabled(true);

        resetPasswordRepository.save(resetPassword);

        return new ResponseBody(
                HttpStatus.OK.value(),
                "Reset Token Request Accepted.",
                LocalDateTime.now()
        );
    }

    public ResponseBody changePassword(ChangePasswordDto changePasswordDto) {

        boolean isValidEmail = emailValidation.test(changePasswordDto.getEmail());

        if (!isValidEmail)
            throw new RuntimeException("There is no such email.");

        User user = userService.getByEmail(changePasswordDto.getEmail());

        if(!isPasswordSame(changePasswordDto))
            throw new RuntimeException("Passwords not same!");

        user.setPassword(changePasswordDto.getNewPassword());
        userService.saveUser(user);

        return new ResponseBody(
                HttpStatus.OK.value(),
                "Password Changed Successfully.",
                LocalDateTime.now()
        );
    }

    private boolean isEnabled(ResetPassword resetPassword) {
        return resetPassword.isEnabled();
    }

    private boolean isExpired(ResetPassword resetPassword) {
        return resetPassword.getExpireDate().isBefore(LocalDateTime.now());
    }

    private boolean isPasswordSame(ChangePasswordDto changePasswordDto) {
        return changePasswordDto.getNewPassword().equals(changePasswordDto.getNewPasswordAgain());
    }

}