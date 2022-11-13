package tk.rottencucumber.backend.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;
import java.time.Year;
import java.util.Set;

@Data
@Entity
@Table(name="movies")
public class MoviesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable
    Set<GenresModel> genres;
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private LanguagesModel language;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable
    Set<ActorsModel> actors;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable
    Set<DirectorsModel> directors;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable
    Set<WritersModel> writers;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable
    Set<PlatformsModel> platforms;
    @Column(name = "summary")
    private String summary;
    @Column(name = "preview", nullable = false)
    private String preview;
    @Lob
    @Column(name = "image")
    private Blob image;
    @Column(name = "year", nullable = false)
    private Year year;
    //Reverse relations
    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private Set<ReviewsModel> reviews;
}
