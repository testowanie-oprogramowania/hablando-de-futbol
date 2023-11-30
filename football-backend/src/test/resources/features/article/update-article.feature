Feature: post article
  Scenario: I can see the newly updated article
    Given I have article text prepared
    Given I create an article
    When I have an update for the article
    When I update the article
    Then I see updated article

