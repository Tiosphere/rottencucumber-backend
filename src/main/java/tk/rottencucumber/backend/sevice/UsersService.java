package tk.rottencucumber.backend.sevice;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.UsersModel;
import tk.rottencucumber.backend.repository.UsersRepository;

@Service
@NoArgsConstructor
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public void createUser(String username, String email, String password) {
        UsersModel newUser = new UsersModel();
        newUser.setUsername(username);
        newUser.setEmail(email);
        String hash = passwordEncoder.encode(password);
        newUser.setPassword(hash);
        usersRepository.save(newUser);
    }

    public void newPassword(UsersModel user, String password) {
        String hash = passwordEncoder.encode(password);
        user.setPassword(hash);
        usersRepository.save(user);
    }

    public UsersModel findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public UsersModel findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public UsersModel findByToken(String token) {
        return usersRepository.findByPasswordEndingWith(token);
    }
}
