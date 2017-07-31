package steps.walletHubSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import pages.facebook.HomePage;
import pages.facebook.LoginPage;
import steps.BaseSteps;

import static org.hamcrest.MatcherAssert.assertThat;

public class FacebookSteps extends BaseSteps {

    @Autowired
    private LoginPage loginPage;

    @Autowired
    private HomePage homePage;

    @Given("^I am on the Facebook login page$")
    public void i_am_on_the_Facebook_login_page() throws Throwable {
        getWrappedDriver().get("http://www.facebook.com");
    }

    @Given("^I enter email or phone as (.*)$")
    public void i_enter_email_or_phone_as(String userName) throws Throwable {
        loginPage.enterUserName(userName);
    }

    @Given("^I enter password (.*)$")
    public void i_enter_password(String password) throws Throwable {
        loginPage.enterPassword(password);
    }

    @Given("^I click on Login$")
    public void i_click_on_Login() throws Throwable {
        loginPage.clickLogin();
    }

    @Then("^My name should be visible as (.*)$")
    public void my_name_should_be_visible_as(String name) throws Throwable {
        assertThat("User name did not match", homePage.getUserName(), Matchers.is(name));
    }

    @Then("^I should be able to post the status message$")
    public void i_should_be_able_to_post_the_status_message() throws Throwable {

        assertThat("Status is not posted", homePage.isStatusPosted(), Matchers.is(false));
    }

}
