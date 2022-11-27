package tk.rottencucumber.backend.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tk.rottencucumber.backend.model.UserModel;
import tk.rottencucumber.backend.record.BoolResponse;
import tk.rottencucumber.backend.record.CodeResponse;
import tk.rottencucumber.backend.security.JWTService;
import tk.rottencucumber.backend.service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UsersService usersService;
    private final JWTService JWTService;

    public AuthController(UsersService usersService, JWTService JWTService) {
        this.usersService = usersService;
        this.JWTService = JWTService;

    }

    private record LoginForm(String username, String password){}

    @PostMapping("/login")
    public BoolResponse Login(HttpServletRequest request, LoginForm form) {
        System.out.println(form.username() + form.password());
        try {
            request.login(form.username(), form.password());
            return new BoolResponse(true, JWTService.generateToken(form.username()));
        } catch (ServletException e) {
            e.printStackTrace();
            return new BoolResponse(false, "Invalid username or password.");
        }
    }

    @GetMapping("/logout")
    public BoolResponse Post(HttpServletRequest request) {
        try {
            request.logout();
            return new BoolResponse(true, "Successfully logout.");
        } catch (ServletException e) {
            return new BoolResponse(false, "Can't logout this user.");
        }
    }

    private record SignupForm(String username, String email, String password1, String password2){}

    @PostMapping("/signup")
    public CodeResponse signup(SignupForm form) {
        String username = form.username().strip();
        String email = form.email().strip();
        String password1 = form.password1().strip();
        String password2 = form.password2().strip();
        String message = passValidator(password1, password2);
        if (message != null) {
            return new CodeResponse(3, message);
        }
        if (username.isBlank()) {
            return new CodeResponse(1, "Username can't be empty.");
        }
        else if (email.isBlank()) {
            return new CodeResponse(2, "Email can't be empty.");
        }
        else if (usersService.findByUsername(username) != null) {
            return new CodeResponse(1, String.format("Username %s has already been taken.", username));
        }
        else if (usersService.findByEmail(email) != null) {
            return new CodeResponse(2, String.format("Email %s has already been taken.", email));
        }
        else {
            usersService.createUser(username, email, password1);
            return new CodeResponse(0, String.format("Successfully created user %s", username));
        }
    }

    private record ForgetPassForm(String email){}

    @PostMapping("/forget")
    public BoolResponse forgetPass(ForgetPassForm form) {
        UserModel user =  usersService.findByEmail(form.email());
        if (user != null) {
            String pass = user.getPassword().substring(200);
            String token = JWTService.generatePassToken(pass);
            return new BoolResponse(true, token);
        } else {
            return new BoolResponse(false, "Can't find user with this email.");
        }
    }

    private record NewPassForm(String password1, String password2) {}
    @PostMapping("/forget/{token}")
    public CodeResponse Post(NewPassForm form, @PathVariable String token) {
        if (!JWTService.valid(token)) {
            return new CodeResponse(2, "Your token was expire");
        }
        String password1 = form.password1();
        String password2 = form.password2();
        String message = passValidator(password1, password2);
        if (message != null) {
            return new CodeResponse(1, message);
        }
        String subject = JWTService.getSubject(token);
        UserModel user = usersService.findByToken(subject);
        if (user != null) {
            usersService.newPassword(user, password1);
            return new CodeResponse(0, "Successfully change password.");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
    }

    private record ChangePassForm(@JsonProperty("old_password") String oldPassword, String password1, String password2){}

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/change/{username}")
    public CodeResponse Post(ChangePassForm form, @PathVariable String username, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (!principal.getName().equals(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This username doesn't exist");
        }
        UserModel user = usersService.findByUsername(username);
        String password1 = form.password1();
        String password2 = form.password2();
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This username doesn't exist");
        }else if (!usersService.checkPassword(user, form.oldPassword())) {
            return new CodeResponse(1, "Invalid password");
        }
        String message = passValidator(password1, password2);
        if (message != null) {
            return new CodeResponse(2, message);
        } else {
            usersService.newPassword(user, password1);
            return new CodeResponse(0, "Successfully change password.");
        }
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
