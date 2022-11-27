package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/api/home")
    public String Get() {
        return "this is home page should have movie";
    }

}
