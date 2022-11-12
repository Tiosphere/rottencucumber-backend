package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import tk.rottencucumber.backend.model.DirectorsModel;

public interface DirectorsRepository extends CrudRepository<DirectorsModel, Long> {
}
