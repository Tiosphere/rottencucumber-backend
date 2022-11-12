package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Setter
@Getter
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<ReviewsModel> reviews;
}
