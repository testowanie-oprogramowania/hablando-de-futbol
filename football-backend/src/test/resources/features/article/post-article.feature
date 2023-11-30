Feature: post article
  Scenario: I can see the newly created article
    Given I have article text prepared
    When I create an article
    When I list all articles
    Then I see new article

