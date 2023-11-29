Feature: post editor
  Scenario: I see newly created editor
    Given I have editor data prepared
    When I create editor profile
    When I list all editors
    Then I see new editor
