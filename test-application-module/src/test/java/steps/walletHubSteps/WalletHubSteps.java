package steps.walletHubSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.hamcrest.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import pages.walletHub.ProfilePage;
import pages.walletHub.ReviewActionPage;
import steps.BaseSteps;

import static org.hamcrest.MatcherAssert.assertThat;

public class WalletHubSteps extends BaseSteps {

    @Autowired
    ProfilePage profilePage;

    @Autowired
    ReviewActionPage reviewActionPage;

    @Given("^I am at profile page$")
    public void i_am_at_profile_page() throws Throwable {
        getWrappedDriver().get("https://wallethub.com/profile/test_insurance_company/");
    }

    @Given("^I select the (\\d+) star rating$")
    public void i_select_the_start_rating(int arg1) throws Throwable {
        profilePage.selectTheRating(3);
    }

    @Given("^I set the policy to Health$")
    public void i_set_the_policy_to_Health() throws Throwable {
        reviewActionPage.selectThePolicy("Health");
    }

    @Given("^I write the review$")
    public void i_write_the_review() throws Throwable {
        reviewActionPage.writeReview();
    }

    @Given("^I press submit$")
    public void i_press_submit() throws Throwable {
        reviewActionPage.submitReview();
    }

    @Then("^I should see a confirmation screen$")
    public void i_should_see_a_confirmation_screen() throws Throwable {
        assertThat("Success message not visible", reviewActionPage.isSuccessMessageVisible(), Matchers.is(true));
    }

    @Then("^I am able to see the feedback comments by (\\w+)$")
    public void i_am_able_to_see_the_feedback_comments(String username) throws Throwable {

        String url = String.format("https://wallethub.com/%s/activity/", username);
        assertThat("Error page should not be displayed",reviewActionPage.isErrorPageVisible(), Matchers.is(false));
    }

}
