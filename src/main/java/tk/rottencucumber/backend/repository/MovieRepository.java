package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.MovieModel;

@Repository
public interface MovieRepository extends CrudRepository<MovieModel, Long> {

    MovieModel findBySlug(String slug);

    Boolean existsBySlug(String slug);
}
