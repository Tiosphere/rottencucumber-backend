package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "genres")
@NoArgsConstructor
public class GenreModel {

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
    private Set<MovieModel> movies;

    public GenreModel(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }
}
