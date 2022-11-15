package tk.rottencucumber.backend.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.rottencucumber.backend.model.UsersModel;
import tk.rottencucumber.backend.sevice.UsersService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user/forget-password/")
public class ForgetPassController {

    @Autowired
    private UsersService usersService;

    @PostMapping()
    public String Post(HttpServletRequest request) {
        String email = request.getParameter("email");
        UsersModel user =  usersService.findByEmail(email);
        if (user != null) {
            String pass = user.getPassword().substring(200);
            String token = NewPassTokenGenerator.encrypt(pass);
            return String.format("Go to: /api/user/forget-password/%s/", token);
        } else {
            return "Can't find user with this email";
        }
    }

    @GetMapping
    public String Get() {
        return "This is forget password page";
    }
}
