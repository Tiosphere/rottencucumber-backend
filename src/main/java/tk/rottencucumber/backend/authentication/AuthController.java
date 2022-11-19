package tk.rottencucumber.backend.authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.rottencucumber.backend.model.UsersModel;
import tk.rottencucumber.backend.sevice.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UsersService usersService;
    private PasswordEncoder passwordEncoder;

    private record LoginForm(String username, String password){}
    private record LoginResponse(Boolean success, String message){}

    @PostMapping("/login")
    public LoginResponse Login(HttpServletRequest request, LoginForm loginForm) {
        try {
            request.login(loginForm.username(), loginForm.password());
            return new LoginResponse(true, "Successfully login");
        } catch (ServletException e) {
//            e.printStackTrace();
            return new LoginResponse(false, "Invalid username or password");
        }
    }

    private record LogoutResponse(Boolean success, String message){}

    @PostMapping("/logout")
    public LogoutResponse Post(HttpServletRequest request, HttpSession session) {
        try {
            request.logout();
            session.invalidate();
            return new LogoutResponse(true, "Successfully logout");
        } catch (ServletException e) {
            return new LogoutResponse(false, "Can't logout this user");
        }
    }

    private record SignupForm(String username, String email, String password1, String password2){}

    private record SignupResponse(Integer code, String message){}

    @PostMapping("/signup")
    public SignupResponse signup(SignupForm signupForm) {
        String username = signupForm.username().strip();
        String email = signupForm.email().strip();
        String password1 = signupForm.password1().strip();
        String password2 = signupForm.password2().strip();

        if (username.isBlank()) {
            return new SignupResponse(1, "Username can't be empty.");
        }
        else if (email.isBlank()) {
            return new SignupResponse(2, "Email can't be empty.");
        }
        else if (password1.isBlank() || password2.isBlank()) {
            return new SignupResponse(3, "Password can't be empty.");
        }
        else if (usersService.findByUsername(username) != null) {
            return new SignupResponse(1, String.format("Username %s has already been taken.", username));
        }
        else if (usersService.findByEmail(email) != null) {
            return new SignupResponse(2, String.format("Email %s has already been taken.", email));
        }
        else if (!password1.equals(password2)) {
            return new SignupResponse(3, "Password didn't match.");
        }
        else if (password1.length() < 8) {
            return new SignupResponse(3, "Password must be 8 characters long.");
        }
        else if (password1.contains(" ")) {
            return new SignupResponse(3, "Password can't contain any white space.");
        }
        else if (!Pattern.compile(".*\\d.*").matcher(password1).matches()) {
            return new SignupResponse(3, "Password must contain at least 1 number.");
        }
        else if (!Pattern.compile(".*[A-Z].*").matcher(password1).matches()) {
            return new SignupResponse(3, "Password must contain at least 1 capital letter.");
        }
        else {
            usersService.createUser(username, email, password1);
            return new SignupResponse(0, String.format("Successfully created user %s", username));
        }
    }

    private record ForgetPassForm(String email){}
    private record ForgetPassResponse(Boolean success, String message){}

    @PostMapping("/forget-pass")
    public ForgetPassResponse forgetPass(ForgetPassForm forgetPassForm) {
        UsersModel user =  usersService.findByEmail(forgetPassForm.email());
        if (user != null) {
            String pass = user.getPassword().substring(200);
            String token = NewPassTokenGenerator.encrypt(pass);
            return new ForgetPassResponse(true, token);
        } else {
            return new ForgetPassResponse(false, "Can't find user with this email.");
        }
    }
}
