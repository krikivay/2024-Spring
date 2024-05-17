package ua.karazin.example.controller;

import ua.karazin.example.dto.EditUserDto;
import ua.karazin.example.entities.User;
import ua.karazin.example.service.RoleService;
import ua.karazin.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.regex.Pattern;

@Controller
public class EditUserController {

    private UserService userService;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;

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

    @GetMapping("/admin/edit-user/{id}")
    public ModelAndView showPage(@PathVariable Long id, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findById(id);
        if (user == null) {
            modelAndView.setViewName("admin-page");
        } else {
            modelAndView.setViewName("edit-user-page");
            modelAndView.addObject("editUser", new EditUserDto(user));
            modelAndView.addObject("user", userService.findByLogin(principal.getName()));
            modelAndView.addObject("roles", roleService.findAll());
        }
        return modelAndView;
    }

    @PostMapping("/admin/edit-user")
    public String editUser(@Valid @ModelAttribute("editUser") EditUserDto userDto,
                           BindingResult result, Model model) {
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                result.rejectValue("password", "passwordMatch", "Passwords are not equal");
                result.rejectValue("confirmPassword", "passwordMatch", "Passwords are not equal");
                model.addAttribute("roles", roleService.findAll());
                model.addAttribute("editUser", userDto);
                return "edit-user-page";
            } else {
                if (userDto.getPassword().length() < 4 || userDto.getPassword().length() > 16) {
                    result.rejectValue("password", "passwordLength",
                            "Password length must be from 4 to 16 characters");
                    result.rejectValue("confirmPassword", "passwordLength",
                            "Password length must be from 4 to 16 characters");
                    model.addAttribute("roles", roleService.findAll());
                    model.addAttribute("editUser", userDto);
                    return "edit-user-page";
                }
                Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{4,16}$");
                if (!pattern.matcher(userDto.getPassword()).matches()) {
                    result.rejectValue("password", "passwordPattern",
                            "Password must consist of English letters and numbers");
                    result.rejectValue("confirmPassword", "passwordPattern",
                            "Password must consist of English letters and numbers");
                    model.addAttribute("roles", roleService.findAll());
                    model.addAttribute("editUser", userDto);
                    return "edit-user-page";
                }
            }
        } else {
            userDto.setPassword(userDto.getOldPassword());
            userDto.setConfirmPassword(userDto.getOldPassword());
        }
        if (result.hasErrors()) {
            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                model.addAttribute("roles", roleService.findAll());
                model.addAttribute("editUser", userDto);
                return "edit-user-page";
            }
        }
        String password = userDto.getPassword();
        if (!password.equals(userDto.getOldPassword())) {
            password = passwordEncoder.encode(password);
        }
        userDto.setPassword(password);
        userDto.setConfirmPassword(password);
        User user = userDto.toUser();
        user.setRole(roleService.findByName(user.getRole().getName()));
        try {
            userService.update(user);
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
            model.addAttribute("editUser", userDto);
            return "edit-user-page";
        }
        return "redirect:/admin";
    }
}
