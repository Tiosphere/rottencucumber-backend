package tk.rottencucumber.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
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
    @JsonManagedReference
    private Set<MovieModel> movies;

    public LanguageModel(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }
}
