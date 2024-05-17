package ua.karazin.example.controller;

import ua.karazin.example.entities.User;
import ua.karazin.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ModelAndView showPage(Principal principal) {
        User user = userService.findByLogin(principal.getName());
        return new ModelAndView("user-page", "user", user);
    }
}
