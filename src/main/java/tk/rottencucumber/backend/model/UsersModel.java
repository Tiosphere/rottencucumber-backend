package tk.rottencucumber.backend.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;


@Data
@Entity
@Table(name="users")
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Lob
    @Column(name = "image")
    private Blob image;
    @Column(name = "is_staff")
    private boolean is_staff;
    //Reverse relations
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<ReviewsModel> reviews;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<LikesModel> likes;
}
