package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.LikesModel;

@Repository
public interface LikesRepository extends CrudRepository<LikesModel, Long> {
}
