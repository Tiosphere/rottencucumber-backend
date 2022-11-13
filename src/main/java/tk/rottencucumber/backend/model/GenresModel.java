package tk.rottencucumber.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="genres")
public class GenresModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    //Reverse relations
    @ManyToMany(mappedBy = "genres", cascade = CascadeType.REMOVE)
    private Set<MoviesModel> movies;
}
