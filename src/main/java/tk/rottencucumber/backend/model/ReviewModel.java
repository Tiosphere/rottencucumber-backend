package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import tk.rottencucumber.backend.model.extend.AbstractModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reviews")
@NoArgsConstructor
public class ReviewModel extends AbstractModel {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false, updatable = false)
    private MovieModel movie;
    @Column(name = "comment", columnDefinition = "mediumtext")
    private String comment;
    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created = LocalDateTime.now();

    public ReviewModel(UserModel user, MovieModel movie, String comment) {
        this.user = user;
        this.movie = movie;
        this.comment = comment;
    }
}
