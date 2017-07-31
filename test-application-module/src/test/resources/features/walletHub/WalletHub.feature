Feature: Submit the review

  @wallethub
  Scenario Outline: I should be able to submit the review
    Given I am at profile page
    And I select the <count> star rating
    And I set the policy to <policyOption>
    And I write the review
    And I press submit
    Then I should see a confirmation screen
    Then I am able to see the feedback comments by <accountName>
    Examples: Scenario data
      | count | policyOption | accountName |
      | 3     | Health       | hemant      |