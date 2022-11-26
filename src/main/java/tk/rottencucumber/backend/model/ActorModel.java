package tk.rottencucumber.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name="actors")
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
    //Reverse relations
    @ManyToMany(mappedBy = "actors", cascade = CascadeType.REMOVE)
    private Set<MovieModel> movies;
}
