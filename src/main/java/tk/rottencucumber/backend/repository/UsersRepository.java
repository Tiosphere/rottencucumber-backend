package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import tk.rottencucumber.backend.model.UsersModel;

public interface UsersRepository extends CrudRepository<UsersModel, Long> {
}
