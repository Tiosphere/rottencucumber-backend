package tk.rottencucumber.backend.sevice;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.UserModel;
import tk.rottencucumber.backend.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(String username, String email, String password) {
        usersRepository.save(new UserModel(username, email, passwordEncoder.encode(password)));
    }

    public boolean checkPassword(UserModel user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public void newPassword(UserModel user, String password) {
        String hash = passwordEncoder.encode(password);
        user.setPassword(hash);
        usersRepository.save(user);
    }

    public UserModel findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public UserModel findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public UserModel findByToken(String token) {
        return usersRepository.findByPasswordEndingWith(token);
    }
}
