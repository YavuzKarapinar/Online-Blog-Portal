package me.jazzy.obp.dto;

import lombok.Data;

@Data
public class ContactDto {
    private String email;
    private String subject;
    private String name;
    private String message;
}