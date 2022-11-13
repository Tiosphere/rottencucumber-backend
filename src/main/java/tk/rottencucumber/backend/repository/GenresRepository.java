package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.GenresModel;

@Repository
public interface GenresRepository extends CrudRepository<GenresModel, Long> {
}
