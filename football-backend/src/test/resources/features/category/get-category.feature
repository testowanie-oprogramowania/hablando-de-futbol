Feature: get existing category
  Scenario: I can see specific category
    Given I have a category
    Given I create new category
    When I request category by its id
    Then I get details of requested category
