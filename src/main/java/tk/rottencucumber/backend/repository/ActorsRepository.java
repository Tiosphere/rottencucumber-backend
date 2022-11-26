package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.ActorModel;

@Repository
public interface ActorsRepository extends CrudRepository<ActorModel, Long> {

    public ActorModel findByName(String name);

}
