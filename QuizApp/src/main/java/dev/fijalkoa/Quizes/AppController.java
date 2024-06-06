package dev.fijalkoa.Quizes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/home")
public class AppController {

    @GetMapping
    public String getHomePage() {
        return "home";
    }
}
