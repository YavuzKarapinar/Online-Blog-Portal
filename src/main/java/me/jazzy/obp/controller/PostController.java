package me.jazzy.obp.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import me.jazzy.obp.dto.LikeDto;
import me.jazzy.obp.dto.PostDto;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.model.Post;
import me.jazzy.obp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/posts")
@AllArgsConstructor
@RestController
@SecurityRequirement(name = "obp")
public class PostController {

    private final PostService postService;

    @GetMapping("/{name}")
    public ResponseEntity<List<Post>> getAllByCategoryName(@PathVariable String name) {
        return new ResponseEntity<>(postService.getAllByCategoryName(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseBody> savePost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.savePost(postDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBody> updatePost(@PathVariable Long id,
                                                   @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.updatePost(id, postDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseBody> likePost(@RequestBody LikeDto likeDto) {
        return new ResponseEntity<>(postService.likePost(likeDto), HttpStatus.OK);
    }
}