package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.ReviewModel;

@Repository
public interface ReviewRepository extends CrudRepository<ReviewModel, Long> {
}
