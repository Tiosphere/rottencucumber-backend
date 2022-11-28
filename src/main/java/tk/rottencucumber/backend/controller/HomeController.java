package tk.rottencucumber.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Dictionary;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String Home() {

        return "this is home page should have movie";
    }

    private record HomeResponse(Boolean success, Dictionary<String, Object> data) {
    }

}
