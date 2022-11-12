package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import tk.rottencucumber.backend.model.ReviewsModel;

public interface ReviewsRepository extends CrudRepository<ReviewsModel, Long> {
}
