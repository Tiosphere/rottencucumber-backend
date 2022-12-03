package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.rottencucumber.backend.model.extend.StarterModel;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "writers")
@NoArgsConstructor
public class WriterModel extends StarterModel {

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
    @ManyToMany(mappedBy = "writers", cascade = CascadeType.REMOVE)
    private Set<MovieModel> movies;

    public WriterModel(String name, String slug, String type, byte[] image, String birthPlace, LocalDate birthday, String description) {
        this.name = name;
        this.slug = slug;
        this.type = type;
        this.image = image;
        this.birthPlace = birthPlace;
        this.birthday = birthday;
        this.description = description;
    }
}
