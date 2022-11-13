package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "likes")
public class LikesModel {

    @Id
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersModel user;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewsModel review;
}
