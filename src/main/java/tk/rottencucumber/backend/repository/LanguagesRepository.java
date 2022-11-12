package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import tk.rottencucumber.backend.model.LanguagesModel;

public interface LanguagesRepository extends CrudRepository<LanguagesModel, Long> {
}
