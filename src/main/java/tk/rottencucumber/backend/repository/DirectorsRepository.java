package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.DirectorsModel;

@Repository
public interface DirectorsRepository extends CrudRepository<DirectorsModel, Long> {
}
