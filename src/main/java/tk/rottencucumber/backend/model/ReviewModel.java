package tk.rottencucumber.backend.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "reviews")
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
    @Column(name = "comment")
    private String comment;
    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created;
    //Reverse relations
    @OneToMany(mappedBy = "review")
    private Set<LikeModel> likes;
}
