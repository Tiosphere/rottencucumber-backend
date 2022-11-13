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
        String hashpass = passwordEncoder.encode(password);
        System.out.println(hashpass.length());
        newUser.setPassword(hashpass);
        usersRepository.save(newUser);
    }

    public UsersModel findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
