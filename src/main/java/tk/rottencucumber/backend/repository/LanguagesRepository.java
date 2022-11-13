package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.LanguagesModel;

@Repository
public interface LanguagesRepository extends CrudRepository<LanguagesModel, Long> {
}
