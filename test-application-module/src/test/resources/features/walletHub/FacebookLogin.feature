Feature: Facebook user is able to post a status message

  @wallethub
  Scenario Outline: I should be able to post a status message right after login to Facebook account
    Given I am on the Facebook login page
    And I enter email or phone as <userName>
    And I enter password <password>
    And I click on Login
    Then My name should be visible as <accountName>
    Then I should be able to post the status message
    Examples: Scenario data
      | userName              | password  | accountName |
      | hemantojhaa@gmail.com | India@100 | Hemant      |