package me.jazzy.obp.dto;

import lombok.Data;

@Data
public class CommentDto {

    private String context;
    private String email;
    private Long postId;
}