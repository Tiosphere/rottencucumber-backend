package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.ReviewsModel;

@Repository
public interface ReviewsRepository extends CrudRepository<ReviewsModel, Long> {
}
