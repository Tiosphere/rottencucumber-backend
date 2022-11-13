package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name="writers")
public class WritersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    @Lob
    @Column(name = "image")
    private Blob image;
    //Reverse relations
    @ManyToMany(mappedBy = "writers", cascade = CascadeType.REMOVE)
    private Set<MoviesModel> movies;
}
