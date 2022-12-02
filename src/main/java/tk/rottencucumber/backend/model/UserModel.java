package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "is_staff")
    private boolean is_staff;
    //Reverse relations
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<ReviewModel> reviews;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<LikeModel> likes;
    public UserModel(String username, String slug, String email, String password, Boolean isStaff) {
        this.username = username;
        this.slug = slug;
        this.email = email;
        this.password = password;
        this.is_staff = isStaff;
    }
}
