package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.DirectorModel;

@Repository
public interface DirectorRepository extends CrudRepository<DirectorModel, Long> {

    boolean existsBySlug(String slug);

    DirectorModel findBySlug(String slug);

    Iterable<DirectorModel> findAllByNameContainsIgnoreCase(String name);
}
