package ua.karazin.example.controller;

import ua.karazin.example.dto.UserDto;
import ua.karazin.example.entities.User;
import ua.karazin.example.service.RoleService;
import ua.karazin.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AddUserController {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/admin/add-user")
    public ModelAndView showPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        User adminName = userService.findByLogin(principal.getName());
        modelAndView.addObject("user", adminName);
        modelAndView.addObject("newUser", new UserDto());
        modelAndView.addObject("roles", roleService.findAll());
        modelAndView.setViewName("add-user-page");
        return modelAndView;
    }

    @PostMapping("/admin/add-user")
    public String addUser(@Valid @ModelAttribute("newUser") UserDto userDto, BindingResult result, Model model) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            result.rejectValue("password", "passwordMatch", "Passwords are not equal");
            result.rejectValue("confirmPassword", "passwordMatch", "Passwords are not equal");
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("newUser", userDto);
            return "add-user-page";
        }
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("newUser", userDto);
            return "add-user-page";
        }
        String password = userDto.getPassword();
        userDto.setPassword(passwordEncoder.encode(password));
        userDto.setConfirmPassword(passwordEncoder.encode(password));
        User user = userDto.toUser();
        user.setRole(roleService.findByName(user.getRole().getName()));
        try {
            userService.create(user);
            return "redirect:/admin";
        } catch (Exception ex) {
            if (userService.findByLogin(user.getLogin()) != null) {
                result.rejectValue("login", "existLogin", "User with this login is already exist");
            }
            if (userService.findByEmail(user.getEmail()) != null) {
                result.rejectValue("email", "existEmail", "User with this email is already exist");
            }
            userDto.setPassword(password);
            userDto.setConfirmPassword(password);
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("newUser", userDto);
            return "add-user-page";
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
