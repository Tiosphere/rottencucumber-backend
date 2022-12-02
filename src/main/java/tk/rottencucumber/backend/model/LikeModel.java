package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "likes")
public class LikeModel {

    @Id
    @Column(name = "id")
    private Long id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "review_id", nullable = false)
    private ReviewModel review;
}
