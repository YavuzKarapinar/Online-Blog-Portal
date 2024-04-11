package me.jazzy.obp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import me.jazzy.obp.dto.CommentDto;
import me.jazzy.obp.dto.LikeDto;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.model.Comment;
import me.jazzy.obp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/comments")
@RestController
@AllArgsConstructor
@SecurityRequirement(name = "obp")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<Comment>> getAll() {
        return new ResponseEntity<>(commentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getAllByPostId(@PathVariable Long postId) {
        return new ResponseEntity<>(commentService.getAllFromPost(postId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> saveComment(@RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.save(commentDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseBody> likeComment(@RequestBody LikeDto likeDto) {
        return new ResponseEntity<>(commentService.likeComment(likeDto), HttpStatus.OK);
    }

}