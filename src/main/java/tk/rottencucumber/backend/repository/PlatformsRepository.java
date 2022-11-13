package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.PlatformsModel;

@Repository
public interface PlatformsRepository extends CrudRepository<PlatformsModel, Long> {
}
