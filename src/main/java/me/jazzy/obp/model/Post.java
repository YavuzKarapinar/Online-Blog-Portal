package me.jazzy.obp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate shareDate;

    private String context;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @OneToMany
    private List<Comment> comments;

    private Long likes;

    public Post(String context,
                User user,
                Category category,
                LocalDate shareDate,
                Long likes,
                List<Comment> comments) {
        this.shareDate = shareDate;
        this.context = context;
        this.user = user;
        this.category = category;
        this.comments = comments;
        this.likes = likes;
    }
}