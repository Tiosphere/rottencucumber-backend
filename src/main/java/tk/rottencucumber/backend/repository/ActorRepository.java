package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.ActorModel;

@Repository
public interface ActorRepository extends CrudRepository<ActorModel, Long> {

    boolean existsBySlug(String slug);

    ActorModel findBySlug(String slug);
}
