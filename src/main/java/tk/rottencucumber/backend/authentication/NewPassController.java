package tk.rottencucumber.backend.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.rottencucumber.backend.model.UsersModel;
import tk.rottencucumber.backend.sevice.UsersService;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user/forget-password/{token}/")
public class NewPassController {

    @Autowired
    private UsersService usersService;

    @PostMapping()
    public String Post(HttpServletRequest request, @PathVariable String token) {
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
        UsersModel user = usersService.findByToken(NewPassTokenGenerator.decrypt(token));
//        UsersModel user =  usersService.findByEmail(TokenGenerator.decrypt(token));
        if (!password1.equals(password2)) {
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
            usersService.newPassword(user, password1);
            return "Successfully change password";
        }
        return "Can't change your password";
    }

    @GetMapping
    public String Get() {
        return "This is forget password page";
    }
}
