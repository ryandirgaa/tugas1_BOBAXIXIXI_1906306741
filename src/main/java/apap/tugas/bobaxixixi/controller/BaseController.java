package apap.tugas.bobaxixixi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class BaseController {
    @GetMapping("/")
    private String home(Model model){
        return "home";
    }
}
