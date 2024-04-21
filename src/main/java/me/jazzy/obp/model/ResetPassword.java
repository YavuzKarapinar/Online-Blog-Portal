package me.jazzy.obp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class ResetPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private boolean isEnabled;
    private LocalDateTime expireDate;

    @ManyToOne
    private User user;

    public ResetPassword(UUID uuid,
                         User user,
                         boolean isEnabled,
                         LocalDateTime expireDate) {
        this.uuid = uuid;
        this.user = user;
        this.isEnabled = isEnabled;
        this.expireDate = expireDate;
    }
}