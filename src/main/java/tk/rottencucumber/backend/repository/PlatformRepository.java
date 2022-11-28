package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.PlatformModel;

@Repository
public interface PlatformRepository extends CrudRepository<PlatformModel, Long> {
    PlatformModel findBySlug(String slug);

    Boolean existsBySlug(String slug);
}
