package ua.karazin.example.controller;

import ua.karazin.example.entities.User;
import ua.karazin.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {

    private UserService userService;

    @GetMapping("/admin")
    public ModelAndView showPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.findAll();
        User user = userService.findByLogin(principal.getName());
        modelAndView.addObject("users", users);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin-page");
        return modelAndView;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
