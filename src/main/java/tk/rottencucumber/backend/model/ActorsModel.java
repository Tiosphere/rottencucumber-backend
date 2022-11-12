package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name="actors")
public class ActorsModel {

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

    @ManyToMany(mappedBy = "actors", cascade = CascadeType.REMOVE)
    private Set<MoviesModel> movies;
}
