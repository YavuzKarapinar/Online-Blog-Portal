package me.jazzy.obp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseBody {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}