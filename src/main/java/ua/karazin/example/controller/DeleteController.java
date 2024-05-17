package ua.karazin.example.controller;

import ua.karazin.example.entities.User;
import ua.karazin.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DeleteController {

    private UserService userService;

    @RequestMapping(value = "/admin/delete-user/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteUser(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findById(id);
        if (user != null) {
            userService.remove(user);
        }
        modelAndView.setViewName("redirect:/admin");
        return modelAndView;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
