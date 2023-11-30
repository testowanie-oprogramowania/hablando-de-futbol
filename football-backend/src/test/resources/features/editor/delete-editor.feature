Feature: delete editor
  Scenario: I cant see deleted editor
    Given I have editor data prepared
    When I create editor profile
    When I delete the editor
    Then I dont see the editor anymore