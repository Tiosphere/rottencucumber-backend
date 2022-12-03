package tk.rottencucumber.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tk.rottencucumber.backend.model.extend.StarterModel;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "platforms")
@NoArgsConstructor
public class PlatformModel extends StarterModel {

    //Reverse relations
    @ManyToMany(mappedBy = "platforms", cascade = CascadeType.REMOVE)
    private Set<MovieModel> movies;

    public PlatformModel(String name, String slug) {
        this.name = name;
        this.slug = slug;
    }
}
