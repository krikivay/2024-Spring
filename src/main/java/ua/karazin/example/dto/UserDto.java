package ua.karazin.example.dto;


import ua.karazin.example.entities.Role;
import ua.karazin.example.entities.User;
import ua.karazin.example.entities.UserBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

public class UserDto {

    private Long id;
    private boolean captcha;

    @Size(min = 4, max = 16, message = "Login length must be from 4 to 16 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$", message = "Login must consist of letters of the English alphabet and numbers")
    private String login;

    @Size(min = 4, max = 16, message = "Password length must be from 4 to 16 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$", message = "Password must consist of English letters and numbers")
    private String password;

    @Size(min = 4, max = 16, message = "Password length must be from 4 to 16 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$", message = "Password must consist of English letters and numbers")
    private String confirmPassword;

    @Email(message = "Email should look like: jsmith@example.com")
    @Pattern(regexp = "^([a-zA-Z0-9._]+)@([a-zA-Z0-9]+)\\.([a-zA-z])+$", message = "Email should look like: jsmith@example.com")
    private String email;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "The name must contain only the letters of the English alphabet")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "The surname must contain only the letters of the English alphabet")
    private String lastName;

    private Date birthday;

    public Role role;

    public UserDto() {

    }

    public UserDto(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = this.confirmPassword = user.getPassword();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.birthday = user.getBirthday();
        this.role = user.getRole();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isCaptcha() {
        return captcha;
    }

    public void setCaptcha(boolean captcha) {
        this.captcha = captcha;
    }

    public User toUser() {
        return new UserBuilder().setId(this.id).setLogin(this.login).setPassword(this.password)
                .setEmail(this.email).setFirstName(this.firstName)
                .setLastName(this.lastName).setBirthday(this.birthday)
                .setRole(this.role).createUser();
    }

    @Override
    public String toString() {
        return "UserDto{" + "id=" + id + ", login='" + login + '\'' + ", password='" + password
                + '\'' + ", confirmPassword='" + confirmPassword + '\''
                + ", email='" + email + '\'' + ", firstName='" + firstName
                + '\'' + ", lastName='" + lastName + '\'' + ", birthday="
                + birthday + ", role=" + role + '}';
    }
}
