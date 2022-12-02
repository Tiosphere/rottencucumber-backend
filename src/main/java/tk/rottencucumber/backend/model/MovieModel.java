package tk.rottencucumber.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name = "movies")
@NoArgsConstructor
public class MovieModel {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable
    private Set<GenreModel> genres;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable
    private Set<ActorModel> actors;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable
    private Set<DirectorModel> directors;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable
    private Set<WriterModel> writers;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable
    private Set<PlatformModel> platforms;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;
    @Column(name = "summary", columnDefinition = "mediumtext")
    private String summary;
    @Column(name = "preview", nullable = false)
    private String preview;
    @Lob
    @Column(name = "image")
    private byte[] image;
    @Column(name = "views")
    private Integer views = 0;
    @Column(name = "type")
    private String type;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private LanguageModel language;
    @Column(name = "release_date", nullable = false)
    private LocalDate release;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private Set<ReviewModel> reviews;

    public MovieModel(String name, String slug, String preview, LocalDate release, LanguageModel language, Set<ActorModel> actors, Set<WriterModel> writers, Set<DirectorModel> directors, Set<GenreModel> genres, Set<PlatformModel> platforms, byte[] image, String type, String summary) {
        this.name = name;
        this.slug = slug;
        this.preview = preview;
        this.release = release;
        this.language = language;
        this.actors = actors;
        this.writers = writers;
        this.directors = directors;
        this.genres = genres;
        this.platforms = platforms;
        this.image = image;
        this.type = type;
        this.summary = summary;
    }
}
