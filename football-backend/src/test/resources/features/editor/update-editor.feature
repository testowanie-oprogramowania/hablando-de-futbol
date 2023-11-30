Feature: update editor
  Scenario: I can see the newly updated editor
    Given I have editor data prepared
    Given I create editor profile
    When I have an update for the editor
    When I update the editor
    Then I see updated editor