package tk.rottencucumber.backend.model.extend;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class StarterModel extends AbstractModel {

    @Column(name = "name", nullable = false, unique = true)
    protected String name;
    @Column(name = "slug", nullable = false, unique = true)
    protected String slug;

}
