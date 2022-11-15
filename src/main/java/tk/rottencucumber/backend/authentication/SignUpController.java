package tk.rottencucumber.backend.authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/signup")
public class SignUpController {

    @PostMapping
    public String Post() {
        return "POST - SIGNUP";
    }

    @GetMapping
    public String Get() {
        return "This is signup page";
    }
}
