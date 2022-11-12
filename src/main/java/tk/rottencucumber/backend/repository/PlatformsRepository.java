package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import tk.rottencucumber.backend.model.PlatformsModel;

public interface PlatformsRepository extends CrudRepository<PlatformsModel, Long> {
}
