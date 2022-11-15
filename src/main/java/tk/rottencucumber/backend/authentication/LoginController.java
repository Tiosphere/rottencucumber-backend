package tk.rottencucumber.backend.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user/login/")
public class LoginController {

    @PostMapping()
    public String Post(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            request.login(username, password);
            return "Successfully login";
        } catch (ServletException e) {
            e.printStackTrace();
            return "Username or password invalid";
        }
    }

    @GetMapping
    public String Get() {
        return "This is login page";
    }
}
