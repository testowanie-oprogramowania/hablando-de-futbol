Feature: get existing category
  Scenario: I can see specific category
    Given I have editor data prepared
    Given I create editor profile
    When I request an editor by its id
    Then I get details of requested editor
