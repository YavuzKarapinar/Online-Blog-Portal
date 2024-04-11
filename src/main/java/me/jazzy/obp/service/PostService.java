package me.jazzy.obp.service;

import lombok.AllArgsConstructor;
import me.jazzy.obp.dto.LikeDto;
import me.jazzy.obp.dto.PostDto;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.model.Post;
import me.jazzy.obp.model.User;
import me.jazzy.obp.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public Post getById(Long id) {
        return postRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("There is no such post."));
    }

    public ResponseBody savePost(PostDto postDto) {
        User user = userService.getByEmail(postDto.getBloggerEmail());

        Post post = new Post(
                postDto.getContext(),
                user,
                LocalDate.now(),
                0L,
                null
        );

        postRepository.save(post);

        return new ResponseBody(
                HttpStatus.CREATED.value(),
                "Post Created.",
                LocalDateTime.now()
        );
    }

    public ResponseBody updatePost(Long id, PostDto postDto) {
        Post post = getById(id);

        post.setContext(postDto.getContext());

        postRepository.save(post);

        return new ResponseBody(
                HttpStatus.OK.value(),
                "Post Updated.",
                LocalDateTime.now()
        );
    }

    public ResponseBody likePost(LikeDto likeDto) {

        Post post = postRepository.findById(likeDto.getId())
                .orElseThrow(() -> new RuntimeException("There is no such post."));

        post.setLikes(post.getLikes() + likeDto.getQuantity());

        postRepository.save(post);

        return new ResponseBody(
                HttpStatus.OK.value(),
                "Post liked.",
                LocalDateTime.now()
        );
    }

}