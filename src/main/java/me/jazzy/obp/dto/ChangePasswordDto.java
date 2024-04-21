package me.jazzy.obp.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String email;
    private String newPassword;
    private String newPasswordAgain;
}