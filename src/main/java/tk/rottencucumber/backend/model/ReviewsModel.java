package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name="reviews")
public class ReviewsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private UsersModel user;
    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false, updatable = false)
    private MoviesModel movie;
    @Column(name = "rated", nullable = false)
    private Integer rated;
    @Column(name = "comment")
    private String comment;
    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime created;
    //Reverse relations
    @OneToMany(mappedBy = "review")
    private Set<LikesModel> likes;
}
