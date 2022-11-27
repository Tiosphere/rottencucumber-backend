package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.ActorModel;

@Repository
public interface ActorRepository extends CrudRepository<ActorModel, Long> {

    ActorModel findByName(String name);
    boolean existsBySlug(String slug);

}
