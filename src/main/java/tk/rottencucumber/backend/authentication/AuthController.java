package tk.rottencucumber.backend.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tk.rottencucumber.backend.model.UserModel;
import tk.rottencucumber.backend.record.response.BoolResponse;
import tk.rottencucumber.backend.record.response.CodeResponse;
import tk.rottencucumber.backend.record.response.ObjectResponse;
import tk.rottencucumber.backend.record.user.UserRecordTool;
import tk.rottencucumber.backend.security.JWTService;
import tk.rottencucumber.backend.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JWTService JWTService;

    public AuthController(UserService userService, JWTService JWTService) {
        this.userService = userService;
        this.JWTService = JWTService;
    }

    @PostMapping("/signup")
    public CodeResponse signup(SignupForm form) {
        String username = form.username().strip();
        String email = form.email().strip();
        String password1 = form.password1().strip();
        String password2 = form.password2().strip();
        String message = passValidator(password1, password2);
        if (message != null) {
            return new CodeResponse(3, message);
        } else if (username.isBlank()) {
            return new CodeResponse(1, "Username can't be empty.");
        } else if (email.isBlank()) {
            return new CodeResponse(2, "Email can't be empty.");
        } else if (userService.findByUsername(username) != null) {
            return new CodeResponse(1, String.format("Username %s has already been taken.", username));
        } else if (userService.findByEmail(email) != null) {
            return new CodeResponse(2, String.format("Email %s has already been taken.", email));
        } else {
            userService.createUser(username, email, password1);
            return new CodeResponse(0, String.format("Successfully created user %s", username));
        }
    }

    @PostMapping("/login")
    public ObjectResponse Login(HttpServletRequest request, LoginForm form) {
        try {
            request.login(form.username(), form.password());
            return new ObjectResponse(true, JWTService.generateToken(form.username()), List.of(UserRecordTool.createRecord(userService.findByUsername(form.username()))));
        } catch (ServletException e) {
            return new ObjectResponse(false, "Invalid username or password.", null);
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

    @PostMapping("/forget")
    public BoolResponse forgetPass(ForgetPassForm form) {
        UserModel user = userService.findByEmail(form.email());
        if (user == null) {
            return new BoolResponse(false, "Can't find user with this email.");
        }
        String pass = user.getPassword().substring(200);
        String token = JWTService.generatePassToken(pass);
        return new BoolResponse(true, token);
    }

    @PostMapping("/forget/{token}")
    public CodeResponse newPass(NewPassForm form, @PathVariable String token) {
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
        UserModel user = userService.findByToken(subject);
        if (user != null) {
            userService.newPassword(user, password1);
            return new CodeResponse(0, "Successfully change password.");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid token");
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/change/{username}")
    public CodeResponse Post(ChangePassForm form, @PathVariable String username, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        if (!principal.getName().equals(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This username doesn't exist");
        }
        UserModel user = userService.findByUsername(username);
        String password1 = form.password1();
        String password2 = form.password2();
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This username doesn't exist");
        } else if (!userService.checkPassword(user, form.oldPassword())) {
            return new CodeResponse(1, "Invalid password");
        }
        String message = passValidator(password1, password2);
        if (message != null) {
            return new CodeResponse(2, message);
        } else {
            userService.newPassword(user, password1);
            return new CodeResponse(0, "Successfully change password.");
        }
    }

    private String passValidator(String password1, String password2) {
        if (password1.isBlank() || password2.isBlank()) {
            return "Password can't be empty.";
        } else if (!password1.equals(password2)) {
            return "Password didn't match.";
        } else if (password1.length() < 8) {
            return "Password must be 8 characters long.";
        } else if (password1.contains(" ")) {
            return "Password can't contain any white space";
        } else if (!Pattern.compile(".*\\d.*").matcher(password1).matches()) {
            return "Password must contain at least 1 number.";
        } else if (!Pattern.compile(".*[A-Z].*").matcher(password1).matches()) {
            return "Password must contain at least 1 Capital letter.";
        }
        return null;
    }

    private record SignupForm(String username, String email, String password1, String password2) {
    }

    private record ForgetPassForm(String email) {
    }

    private record NewPassForm(String password1, String password2) {
    }

    private record ChangePassForm(@JsonProperty("old_password") String oldPassword, String password1,
                                  String password2) {
    }

    private record LoginForm(String username, String password) {
    }
}
