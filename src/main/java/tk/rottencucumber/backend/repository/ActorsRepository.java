package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import tk.rottencucumber.backend.model.ActorsModel;

public interface ActorsRepository extends CrudRepository<ActorsModel, Long> {
}
