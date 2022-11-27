package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.LikeModel;

@Repository
public interface LikeRepository extends CrudRepository<LikeModel, Long> {
}