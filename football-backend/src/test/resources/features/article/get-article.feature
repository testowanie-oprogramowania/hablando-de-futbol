Feature: get article
  Scenario: I can see the specific article
    Given I have article text prepared
    Given I create an article
    When I request an article by its id
    Then I get details of requested article

