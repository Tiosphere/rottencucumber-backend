package tk.rottencucumber.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Set;

@Data
@Entity
@Table(name = "directors")
public class DirectorModel {

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
    @Column(name = "type")
    private String type;
    //Reverse relations
    @ManyToMany(mappedBy = "directors", cascade = CascadeType.REMOVE)
    private Set<MovieModel> movies;
}
