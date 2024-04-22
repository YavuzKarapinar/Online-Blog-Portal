package me.jazzy.obp.service;

import lombok.AllArgsConstructor;
import me.jazzy.obp.config.exception.notfound.CommentNotFoundException;
import me.jazzy.obp.config.exception.notfound.UserNotFoundException;
import me.jazzy.obp.dto.CommentDto;
import me.jazzy.obp.dto.LikeDto;
import me.jazzy.obp.dto.ResponseBody;
import me.jazzy.obp.model.Comment;
import me.jazzy.obp.model.Post;
import me.jazzy.obp.model.User;
import me.jazzy.obp.repository.CommentRepository;
import me.jazzy.obp.validator.EmailValidation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final UserService userService;
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final EmailValidation emailValidation;

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public List<Comment> getAllFromPost(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    public ResponseBody save(CommentDto commentDto) {

        boolean isValid = emailValidation.test(commentDto.getEmail());

        if(!isValid)
            throw new UserNotFoundException("There is no such email.");

        User user = userService.getByEmail(commentDto.getEmail());
        Post post = postService.getById(commentDto.getPostId());

        Comment comment = new Comment(
                post,
                user,
                commentDto.getContext(),
                0L
        );

        commentRepository.save(comment);

        return new ResponseBody(
                HttpStatus.CREATED.value(),
                "Comment Added to Post.",
                LocalDateTime.now()
        );
    }

    public ResponseBody likeComment(LikeDto likeDto) {

        Comment comment = commentRepository.findById(likeDto.getId())
                .orElseThrow(() -> new CommentNotFoundException("There is no such comment."));

        comment.setLikes(comment.getLikes() + likeDto.getQuantity());

        commentRepository.save(comment);

        return new ResponseBody(
                HttpStatus.OK.value(),
                "Comment liked.",
                LocalDateTime.now()
        );
    }
}