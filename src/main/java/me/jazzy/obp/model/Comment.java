package me.jazzy.obp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;

    private String context;

    private Long likes;

    public Comment(Post post,
                   User user,
                   String context,
                   Long likes) {
        this.post = post;
        this.user = user;
        this.context = context;
        this.likes = likes;
    }
}