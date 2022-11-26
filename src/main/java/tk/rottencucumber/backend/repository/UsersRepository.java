package tk.rottencucumber.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tk.rottencucumber.backend.model.UserModel;

@Repository
public interface UsersRepository extends CrudRepository<UserModel, Long>{

    UserModel findByEmail(String email);
    UserModel findByUsername(String username);
    UserModel findByPasswordEndingWith(String keyword);
}
