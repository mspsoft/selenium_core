package site;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import site.pages.GatewayPage;
import site.pages.authentication.LoginPage;
import users.Accounts;
import users.User;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created By: Brian Smith on 3/19/14.
 * Description: This test verifies the actions and error messages associated within the main gateway page.
 */
@Description("Verify various home page data elements and controls.")
public class GatewayTest extends BaseTest{
    private static User user = new User("Daenerys", "Targaryen", "dtargaryen@gmail.com",
            "dtargaryen", "Pass1234");
    private GatewayPage gatewayPage;

    @BeforeClass
    public static void createAccountBeforeExecution() {
        Accounts.createAccount(user.getFirstName(), user.getLastName(), user.getEmail(), user.getUserName(),
                user.getPassword());
    }

    @Before
    public void setUp() {
        openBrowser(testName.getMethodName());

        LoginPage loginPage = new LoginPage(localDriver);
        gatewayPage = new GatewayPage(localDriver, loginPage, user.getUserName(), user.getPassword());
        gatewayPage.get();
    }

    @Features("Gateway")
    @Stories("Verify welcome message")
    @Test
    public void verifyWelcomeMessage() throws Exception {
        assertThat("Welcome message is incorrect.", gatewayPage.getWelcomeMessage(),
                is(equalTo("Welcome, " + user.getFirstName() + " " + user.getLastName() + ".")));
    }

    @Features("Gateway")
    @Stories("Logout")
    @Test
    public void logout() throws Exception {
        gatewayPage.logout();
        assertThat("Logout failed.", gatewayPage.getPageTitle(), is(equalTo("TestDriver - Login")));
    }

    @After
    public void tearDown() {
        getScreenShot();
        quit();
    }
}
