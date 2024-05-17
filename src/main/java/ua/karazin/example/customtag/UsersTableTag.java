package ua.karazin.example.customtag;

import ua.karazin.example.entities.User;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static java.lang.Math.abs;

public class UsersTableTag extends SimpleTagSupport {

    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        out.write("<table class=\"table\">" +
                "<thead>" +
                "<tr>" +
                "<th>Login</th>" +
                "<th>First name</th>" +
                "<th>Last name</th>" +
                "<th>Age</th>" +
                "<th>Role</th>" +
                "<th>Actions</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody>");
        for (int i = 0; i < users.size(); i++) {
            Period period = Period.between(LocalDate.now(), users.get(i).getBirthday().toLocalDate());
            int age = abs(period.getYears());
            String rowClass = "round-top";
            if (i == users.size() - 1) {
                rowClass = "round-bottom";
            }
            out.write("<tr class=\"" + rowClass + "\">" +
                    "<td>" + StringEscapeUtils.escapeXml(users.get(i).getLogin()) + "</td>" +
                    "<td>" + StringEscapeUtils.escapeXml(users.get(i).getFirstName()) + "</td>" +
                    "<td>" + StringEscapeUtils.escapeXml(users.get(i).getLastName()) + "</td>" +
                    "<td>" + age + "</td>" +
                    "<td>" + users.get(i).getRole().getName() + "</td>" +
                    "<td><a href=\"/admin/edit-user/" + users.get(i).getId() +
                    "\">Edit</a> <a href=\"/admin\" onclick=\"onBeforeDeleteUser("
                    + users.get(i).getId() + ")\">Delete</a></td></tr>");
        }
        out.write("</tbody></table></div></div></div>");
        out.close();
    }
}
