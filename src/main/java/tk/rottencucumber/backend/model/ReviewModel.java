package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "reviews")
@NoArgsConstructor
public class ReviewModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false, updatable = false)
    private MovieModel movie;
    @Column(name = "rated", nullable = false)
    private Integer rated;
    @Column(name = "comment", columnDefinition = "mediumtext")
    private String comment;
    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created = LocalDateTime.now();
    //Reverse relations
    @OneToMany(mappedBy = "review")
    private Set<LikeModel> likes;

    public ReviewModel(UserModel user, MovieModel movie, Integer rated, String comment) {
        this.user = user;
        this.movie = movie;
        this.rated = rated;
        this.comment = comment;
    }
}
