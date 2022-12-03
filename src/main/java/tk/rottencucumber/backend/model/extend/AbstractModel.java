package tk.rottencucumber.backend.model.extend;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Getter
@Setter
public class AbstractModel {
    @Id
    @GeneratedValue
    @Column(name = "id")
    protected Long id;
}
