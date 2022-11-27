package tk.rottencucumber.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "languages")
@NoArgsConstructor
public class LanguageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    //Reverse relations
    @OneToMany(mappedBy = "language", cascade = CascadeType.REMOVE)
    private Set<MovieModel> movies;

    public LanguageModel(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }
}
