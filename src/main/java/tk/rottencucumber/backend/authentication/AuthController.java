package tk.rottencucumber.backend.authentication;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.rottencucumber.backend.model.UsersModel;
import tk.rottencucumber.backend.security.JWTService;
import tk.rottencucumber.backend.sevice.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UsersService usersService;
    private final JWTService JWTService;

    public AuthController(UsersService usersService, JWTService JWTService) {
        this.usersService = usersService;
        this.JWTService = JWTService;

    }

    private record LoginForm(String username, String password, Boolean remember){}
    private record LoginResponse(Integer code, String message){}

    @PostMapping("/login")
    public LoginResponse Login(HttpServletRequest request, LoginForm form) {
        try {
            request.login(form.username(), form.password());
            if (form.remember()) {
                return new LoginResponse(-1, JWTService.generateToken(form.username()));
            } else {
                return new LoginResponse(0, "Successfully login.");
            }
        } catch (ServletException e) {
            e.printStackTrace();
            return new LoginResponse(1, "Invalid username or password.");
        }
    }

    private record LogoutResponse(Boolean success, String message){}

    @PostMapping("/logout")
    public LogoutResponse Post(HttpServletRequest request, HttpSession session) {
        try {
            request.logout();
            session.invalidate();
            return new LogoutResponse(true, "Successfully logout.");
        } catch (ServletException e) {
            return new LogoutResponse(false, "Can't logout this user.");
        }
    }

    private record SignupForm(String username, String email, String password1, String password2){}

    private record SignupResponse(Integer code, String message){}

    @PostMapping("/signup")
    public SignupResponse signup(SignupForm form) {
        String username = form.username().strip();
        String email = form.email().strip();
        String password1 = form.password1().strip();
        String password2 = form.password2().strip();
        String message = passValidator(password1, password2);
        if (message != null) {
            return new SignupResponse(3, message);
        }
        if (username.isBlank()) {
            return new SignupResponse(1, "Username can't be empty.");
        }
        else if (email.isBlank()) {
            return new SignupResponse(2, "Email can't be empty.");
        }
        else if (usersService.findByUsername(username) != null) {
            return new SignupResponse(1, String.format("Username %s has already been taken.", username));
        }
        else if (usersService.findByEmail(email) != null) {
            return new SignupResponse(2, String.format("Email %s has already been taken.", email));
        }
        else {
            usersService.createUser(username, email, password1);
            return new SignupResponse(0, String.format("Successfully created user %s", username));
        }
    }

    private record ForgetPassForm(String email){}
    private record ForgetPassResponse(Boolean success, String message){}

    @PostMapping("/forget-pass")
    public ForgetPassResponse forgetPass(ForgetPassForm form) {
        UsersModel user =  usersService.findByEmail(form.email());
        if (user != null) {
            String pass = user.getPassword().substring(200);
            String token = JWTService.generatePassToken(pass);
            return new ForgetPassResponse(true, token);
        } else {
            return new ForgetPassResponse(false, "Can't find user with this email.");
        }
    }

    private record NewPassRequest(String password1, String password2){}
    private record NewPassResponse(Boolean success, String message){}
    @PostMapping("/forget-pass/{token}")
    public NewPassResponse Post(NewPassRequest form, @PathVariable String token) {
        if (!JWTService.valid(token)) {
            return new NewPassResponse(false, "Your token was expire");
        }
        String password1 = form.password1();
        String password2 = form.password2();
        String message = passValidator(password1, password2);
        if (message != null) {
            return new NewPassResponse(false, message);
        }
        String subject = JWTService.getSubject(token);
        UsersModel user = usersService.findByToken(subject);
        if (user != null) {
            usersService.newPassword(user, password1);
            return new NewPassResponse(true, "Successfully change password.");
        }
        return new NewPassResponse(false, "Oops! something happen");
    }

    private String passValidator(String password1, String password2) {
        if (password1.isBlank() || password2.isBlank()) {
            return "Password can't be empty.";
        }
        else if (!password1.equals(password2)) {
            return "Password didn't match.";
        }
        else if (password1.length() < 8) {
            return "Password must be 8 characters long.";
        }
        else if (password1.contains(" ")) {
            return "Password can't contain any white space";
        }
        else if (!Pattern.compile(".*\\d.*").matcher(password1).matches()) {
            return "Password must contain at least 1 number.";
        }
        else if (!Pattern.compile(".*[A-Z].*").matcher(password1).matches()) {
            return "Password must contain at least 1 Capital letter.";
        }
        return null;
    }
}
