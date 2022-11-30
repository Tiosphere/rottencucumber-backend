package tk.rottencucumber.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "actors")
@NoArgsConstructor
public class ActorModel {

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
    private byte[] image;
    @Column(name = "type")
    private String type;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "birth_place")
    private String birthPlace;
    @Column(name = "description", columnDefinition = "mediumtext")
    private String description;
    //Reverse relations
    @ManyToMany(mappedBy = "actors", cascade = CascadeType.REMOVE)
    private Set<MovieModel> movies;

    public ActorModel(String name, String slug, String type, byte[] image, String birthPlace, LocalDate birthday, String description) {
        this.name = name;
        this.slug = slug;
        this.type = type;
        this.image = image;
        this.birthPlace = birthPlace;
        this.birthday = birthday;
        this.description = description;
    }
}
