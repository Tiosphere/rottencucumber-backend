package tk.rottencucumber.backend.authentication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/user/logout/")
public class LogOutController {

    @PostMapping
    public String Post(HttpServletRequest request, HttpSession session) {
        try {
            request.logout();
            session.invalidate();
            return "Successfully logout";
        } catch (ServletException e) {
            return "Can't logout";
        }
    }

    @GetMapping
    public String Get() {
        return "GET - LOGOUT";
    }
}
