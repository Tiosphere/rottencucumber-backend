package tk.rottencucumber.backend.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.rottencucumber.backend.sevice.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user/signup")
public class SignUpController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public String Post(HttpServletRequest request) {
        String username = request.getParameter("username").strip();
        String email = request.getParameter("email".strip());
        String password1 = request.getParameter("password1".strip());
        String password2 = request.getParameter("password2".strip());
        
        if (username.isBlank()) {
            request.getSession().setAttribute("message", "Username can't be blank.");
        }
        else if (email.isBlank()) {
            request.getSession().setAttribute("message", "Email can't be blank.");
        }
        else if (password1.isBlank() || password2.isBlank()) {
            request.getSession().setAttribute("message", "Password can't be blank.");
        }
        else if (usersService.findByUsername(username) != null) {
            request.getSession().setAttribute("message", String.format("Username %s has already been taken", username));
        }
        else if (usersService.findByEmail(email) != null) {
            request.getSession().setAttribute("message", String.format("Email %s has already been taken", username));
        }
        else if (!password1.equals(password2)) {
            request.getSession().setAttribute("message", "Password didn't match");
        }
        else if (password1.length() < 8) {
            request.getSession().setAttribute("message", "Password must be 8 characters long.");
        }
        else if (password1.contains(" ")) {
            request.getSession().setAttribute("message", "Password can't contain any white space");
        }
        else if (!Pattern.compile(".*\\d.*").matcher(password1).matches()) {
            request.getSession().setAttribute("message", "Password must contain at least 1 number.");
        }
        else if (!Pattern.compile(".*[A-Z].*").matcher(password1).matches()) {
            request.getSession().setAttribute("message", "Password must contain at least 1 Capital letter.");
        }
        else {
            usersService.createUser(username, email, password1);
            return "Successfully created new user";
        }
        return "Oops, something happened";
    }

    @GetMapping
    public String Get() {
        return "This is signup page";
    }
}
