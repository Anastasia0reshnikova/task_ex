package tests;

import appmanager.ApplicationManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.sql.SQLException;

public class TestBase {

    public static ApplicationManager app;

    @BeforeClass
    public static void setUp(){
        app = new ApplicationManager();
    }

    @Before
    public void openStartPage() {
        app.driver().goToUrl();
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        if (app.driver() != null) {
            app.driver().stopDriver();
        }
    }
}
