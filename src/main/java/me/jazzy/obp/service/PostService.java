package me.jazzy.obp.service;

import lombok.AllArgsConstructor;
import me.jazzy.obp.config.exception.notfound.PostNotFoundException;
import me.jazzy.obp.dto.LikeDto;
import me.jazzy.obp.dto.PostDto;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.model.Category;
import me.jazzy.obp.model.Post;
import me.jazzy.obp.model.User;
import me.jazzy.obp.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public Post getById(Long id) {
        return postRepository.findById(id)
                    .orElseThrow(() -> new PostNotFoundException("There is no such post."));
    }

    public List<Post> getAllByCategoryName(String name) {
        return postRepository.findAllByCategoryName(name);
    }

    public ResponseBody savePost(PostDto postDto) {
        User user = userService.getByEmail(postDto.getBloggerEmail());
        Category category = categoryService.getByName(postDto.getCategoryName());

        Post post = new Post(
                postDto.getContext(),
                user,
                category,
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

        Post post = getById(likeDto.getId());

        post.setLikes(post.getLikes() + likeDto.getQuantity());

        postRepository.save(post);

        return new ResponseBody(
                HttpStatus.OK.value(),
                "Post liked.",
                LocalDateTime.now()
        );
    }

}