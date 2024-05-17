package ua.karazin.example.dao;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RunWith(JUnit4.class)
public abstract class AbstractHibernateDaoTest extends DBTestCase {

    private static final String DB_PROPERTIES_FILE = "db_test.properties";
    private static final String DB_DRIVER = "spring.datasource.driverClassName";
    private static final String DB_URL = "test_db_url";
    private static final String DB_USER = "spring.datasource.username";
    private static final String DB_PASSWORD = "spring.datasource.password";


    public AbstractHibernateDaoTest() {
        try (InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream(DB_PROPERTIES_FILE)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            System.setProperty(
                    PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
                    properties.getProperty(DB_DRIVER));
            System.setProperty(
                    PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
                    properties.getProperty(DB_URL));
            System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
                    properties.getProperty(DB_USER));
            System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
                    properties.getProperty(DB_PASSWORD));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return getDataSet("data.xml");
    }

    protected IDataSet getDataSet(String filename) throws DataSetException {
        return new FlatXmlDataSetBuilder().build(getClass().getClassLoader()
                .getResourceAsStream(filename));
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        IDatabaseConnection connection;
    }


}
