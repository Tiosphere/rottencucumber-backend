package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import tk.rottencucumber.backend.model.MoviesModel;

public interface MoviesRepository extends CrudRepository<MoviesModel, Long> {
}
