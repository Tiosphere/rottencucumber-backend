package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.GenreModel;

@Repository
public interface GenreRepository extends CrudRepository<GenreModel, Long> {
    GenreModel findBySlug(String slug);

    Boolean existsBySlug(String slug);
}
