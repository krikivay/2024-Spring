package ua.karazin.example.dao;

import ua.karazin.example.entities.Role;
import ua.karazin.example.entities.User;
import ua.karazin.example.service.UserService;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class HibernateUserDaoTest extends AbstractHibernateDaoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void createMethodShouldCreateNewUser() throws Exception {
        LocalDate localDate = LocalDate.of(1997, 11, 11);
        Date date = Date.valueOf(localDate);
        Role role = new Role("user");
        role.setId((long) 2);
        User user = new User("vasya.pupkin", "vasya111", "vpupkin@gmail.com",
                "Vasya", "Pupkin", date, role);
        user.setId((long) 6);
        userService.create(user);
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("USER");
        IDataSet expectedDataSet = getDataSet("user_after_create.xml");
        ITable expectedTable = expectedDataSet.getTable("USER");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void createMethodShouldProduceRuntimeExceptionBecauseOfUniqueLogin() {
        LocalDate localDate = LocalDate.of(1997, 11, 11);
        Date date = Date.valueOf(localDate);
        Role role = new Role("user");
        role.setId((long) 2);
        thrown.expect(RuntimeException.class);
        userService.create(
                new User("pupkin", "vasya111", "vpupkin@gmail.com",
                        "Vasya", "Pupkin", date, role));
    }

    @Test
    public void createMethodShouldProduceRuntimeExceptionBecauseOfUniqueEmail() {
        LocalDate localDate = LocalDate.of(1997, 11, 11);
        Date date = Date.valueOf(localDate);
        Role role = new Role("user");
        role.setId((long) 2);
        thrown.expect(RuntimeException.class);
        userService.create(
                new User("vasya.pupkin", "vasya111", "pupkin@gmail.com",
                        "Vasya", "Pupkin", date, role));
    }

    @Test
    public void updateMethodShouldUpdateExistedUser() throws Exception {
        Role role = new Role("admin");
        role.setId((long) 2);
        LocalDate localDate = LocalDate.of(1997, 11, 11);
        Date date = Date.valueOf(localDate);
        User user = new User("vasya.pupkin", "vasya111", "vpupkin@gmail.com",
                "Vasya", "Pupkin", date, role);
        user.setId((long) 5);
        userService.update(user);
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("USER");
        IDataSet expectedDataSet = getDataSet("user_after_update.xml");
        ITable expectedTable = expectedDataSet.getTable("USER");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void removeMethodShouldRemoveExistedUser() throws Exception {
        Role role = new Role("user");
        role.setId((long) 2);
        LocalDate localDate = LocalDate.of(2000, 2, 20);
        Date date = Date.valueOf(localDate);
        User user = new User("pupkin", "321", "pupkin@gmail.com",
                "Pupok", "Pupkin", date, role);
        user.setId((long) 5);
        userService.remove(user);
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("USER");
        IDataSet expectedDataSet = getDataSet("user_after_remove.xml");
        ITable expectedTable = expectedDataSet.getTable("USER");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void findByLoginMethodShouldFindUserByLogin() throws Exception {
        ITable expectedUserTable = getDataSet().getTable("USER");
        ITable expectedRoleTable = getDataSet().getTable("ROLE");
        Role expectedRole = new Role((String) expectedRoleTable.getValue(1, "name"));
        expectedRole.setId((long) 2);
        User expectedUser = new User((String) expectedUserTable.getValue(1, "login"),
                (String) expectedUserTable.getValue(1, "password"),
                (String) expectedUserTable.getValue(1, "email"),
                (String) expectedUserTable.getValue(1, "firstName"),
                (String) expectedUserTable.getValue(1, "lastName"),
                Date.valueOf((String) expectedUserTable.getValue(1, "birthday")), expectedRole);
        expectedUser.setId((long) 2);
        User actualUser = userService.findByLogin("petrov");
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findByLoginMethodShouldReturnNull() {
        User user = userService.findByLogin("bla");
        assertNull(user);
    }

    @Test
    public void findByIdMethodShouldFindUserById() throws Exception {
        ITable expectedUserTable = getDataSet().getTable("USER");
        ITable expectedRoleTable = getDataSet().getTable("ROLE");
        Role expectedRole = new Role((String) expectedRoleTable.getValue(1, "name"));
        expectedRole.setId((long) 2);
        User expectedUser = new User((String) expectedUserTable.getValue(1, "login"),
                (String) expectedUserTable.getValue(1, "password"),
                (String) expectedUserTable.getValue(1, "email"),
                (String) expectedUserTable.getValue(1, "firstName"),
                (String) expectedUserTable.getValue(1, "lastName"),
                Date.valueOf((String) expectedUserTable.getValue(1, "birthday")), expectedRole);
        expectedUser.setId((long) 2);
        User actualUser = userService.findById((long) 2);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findByIdMethodShouldReturnNull() {
        User user = userService.findById(100L);
        assertNull(user);
    }

    @Test
    public void findByEmailMethodShouldFindUserByEmail() throws Exception {
        ITable expectedUserTable = getDataSet().getTable("USER");
        ITable expectedRoleTable = getDataSet().getTable("ROLE");
        Role expectedRole = new Role((String) expectedRoleTable.getValue(1, "name"));
        expectedRole.setId((long) 2);
        User expectedUser = new User((String) expectedUserTable.getValue(1, "login"),
                (String) expectedUserTable.getValue(1, "password"),
                (String) expectedUserTable.getValue(1, "email"),
                (String) expectedUserTable.getValue(1, "firstName"),
                (String) expectedUserTable.getValue(1, "lastName"),
                Date.valueOf((String) expectedUserTable.getValue(1, "birthday")), expectedRole);
        expectedUser.setId((long) 2);
        User actualUser = userService.findByEmail("petrov@karazin.student.ua");
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void findByEmailMethodShouldReturnNull() {
        User user = userService.findByEmail("bla");
        assertNull(user);
    }

    @Test
    public void findAllMethodShouldFindAllUsers() throws Exception {
        ITable expectedUserTable = getDataSet().getTable("USER");
        ITable expectedRoleTable = getDataSet().getTable("ROLE");
        List<Role> roleList = new ArrayList<>();
        for (int i = 0; i < expectedRoleTable.getRowCount(); i++) {
            Role role = new Role((String) expectedRoleTable.getValue(i, "name"));
            role.setId(Long.valueOf((String) expectedRoleTable.getValue(i, "id")));
            roleList.add(role);
        }
        List<User> expectedUsers = new ArrayList<>();
        for (int i = 0; i < expectedUserTable.getRowCount(); i++) {
            User user = new User((String) expectedUserTable.getValue(i, "login"),
                    (String) expectedUserTable.getValue(i, "password"),
                    (String) expectedUserTable.getValue(i, "email"),
                    (String) expectedUserTable.getValue(i, "firstName"),
                    (String) expectedUserTable.getValue(i, "lastName"),
                    Date.valueOf((String) expectedUserTable.getValue(i, "birthday")),
                    roleList.get(Integer.parseInt(expectedUserTable.getValue(i, "role_id").toString()) - 1));
            user.setId(Long.valueOf((String) expectedUserTable.getValue(i, "id")));
            expectedUsers.add(user);
        }
        List<User> actualUsers = userService.findAll();
        assertEquals(expectedUsers, actualUsers);
    }
}
