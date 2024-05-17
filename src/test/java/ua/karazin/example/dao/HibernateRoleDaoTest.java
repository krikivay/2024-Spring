package ua.karazin.example.dao;

import ua.karazin.example.entities.Role;
import ua.karazin.example.service.RoleService;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
public class HibernateRoleDaoTest extends AbstractHibernateDaoTest {

    RoleService roleService;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @Autowired
    public void setRoleDao(RoleService roleService) {
        this.roleService = roleService;
    }

    @Test
    public void createMethodShouldCreateNewRole() throws Exception {
        Role role = new Role("super admin");
        role.setId(3L);
        roleService.create(role);
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("ROLE");
        IDataSet expectedDataSet = getDataSet("role_after_create.xml");
        ITable expectedTable = expectedDataSet.getTable("ROLE");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void updateMethodShouldUpdateExistedRole() throws Exception {
        Role role = new Role("super admin");
        role.setId((long) 1);
        roleService.update(role);
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("ROLE");
        IDataSet expectedDataSet = getDataSet("role_after_update.xml");
        ITable expectedTable = expectedDataSet.getTable("ROLE");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void removeMethodShouldRemoveExistedRole() throws Exception {
        Role role = new Role("admin");
        role.setId((long) 1);
        roleService.remove(role);
        IDataSet actualDataSet = getConnection().createDataSet();
        ITable actualTable = actualDataSet.getTable("ROLE");
        IDataSet expectedDataSet = getDataSet("role_after_remove.xml");
        ITable expectedTable = expectedDataSet.getTable("ROLE");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void findByNameMethodShouldFindRoleByName() throws Exception {
        ITable expectedRoleTable = getDataSet().getTable("ROLE");
        Role expectedRole = new Role((String) expectedRoleTable.getValue(0, "name"));
        expectedRole.setId((long) 1);
        Role actualRole = roleService.findByName("admin");
        assertEquals(expectedRole, actualRole);
    }

    @Test
    public void findByNameMethodShouldReturnNull() {
        Role role = roleService.findByName("super admin");
        assertNull(role);
    }

    @Test
    public void findAllMethodShouldFindAllRoles() throws Exception {
        ITable expectedRoleTable = getDataSet().getTable("ROLE");
        List<Role> roleList = new ArrayList<>();
        for (int i = 0; i < expectedRoleTable.getRowCount(); i++) {
            Role role = new Role((String) expectedRoleTable.getValue(i, "name"));
            role.setId(Long.valueOf((String) expectedRoleTable.getValue(i, "id")));
            roleList.add(role);
        }
        List<Role> actualRoles = roleService.findAll();
        assertEquals(roleList, actualRoles);
    }
}

