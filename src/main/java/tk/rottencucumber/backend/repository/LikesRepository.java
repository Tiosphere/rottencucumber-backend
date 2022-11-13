package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import tk.rottencucumber.backend.model.LikesModel;

public interface LikesRepository extends CrudRepository<LikesModel, Long> {
}
