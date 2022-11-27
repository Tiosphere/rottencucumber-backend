package tk.rottencucumber.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "writers")
@NoArgsConstructor
public class WriterModel {

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
    //Reverse relations
    @ManyToMany(mappedBy = "writers", cascade = CascadeType.REMOVE)
    private Set<MovieModel> movies;

    public WriterModel(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }

    public WriterModel(String name, String slug, String type, byte[] image) {
        this.name = name;
        this.slug = slug;
        this.type = type;
        this.image = image;
    }
}
