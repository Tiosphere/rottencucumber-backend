package tk.rottencucumber.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.rottencucumber.backend.model.extend.StarterModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "languages")
@NoArgsConstructor
public class LanguageModel extends StarterModel {

    //Reverse relations
    @OneToMany(mappedBy = "language", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private Set<MovieModel> movies;

    public LanguageModel(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }
}
