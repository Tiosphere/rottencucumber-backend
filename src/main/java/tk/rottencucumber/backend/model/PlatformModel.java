package tk.rottencucumber.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "platforms")
@NoArgsConstructor
public class PlatformModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    //Reverse relations
    @ManyToMany(mappedBy = "platforms", cascade = CascadeType.REMOVE)
    private Set<MovieModel> movies;

    public PlatformModel(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }
}
