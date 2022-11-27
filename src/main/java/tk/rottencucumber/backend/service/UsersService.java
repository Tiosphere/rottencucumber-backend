package tk.rottencucumber.backend.service;

import com.github.slugify.Slugify;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tk.rottencucumber.backend.model.UserModel;
import tk.rottencucumber.backend.repository.UserRepository;
import tk.rottencucumber.backend.util.Slugifier;

@Service
public class UsersService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void createUser(String username, String email, String password) {
        Slugify slugify = Slugifier.getInstance();
        String slug = slugify.slugify(username);
        while (true) {
            if (userRepository.existsBySlug(slug)) {
                slug = slugify.slugify(slug.concat(RandomString.hashOf(4)));
            } else {
                break;
            }
        }
        userRepository.save(new UserModel(username, slug,  email, passwordEncoder.encode(password)));
    }

    public boolean checkPassword(UserModel user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    public void newPassword(UserModel user, String password) {
        String hash = passwordEncoder.encode(password);
        user.setPassword(hash);
        userRepository.save(user);
    }

    public UserModel findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserModel findByToken(String token) {
        return userRepository.findByPasswordEndingWith(token);
    }
}
