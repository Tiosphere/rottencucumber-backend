package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.UsersModel;

@Repository
public interface UsersRepository extends CrudRepository<UsersModel, Long>{

    UsersModel findByEmail(String email);
    UsersModel findByUsername(String username);

    UsersModel findByPasswordEndingWith(String keyword);
}
