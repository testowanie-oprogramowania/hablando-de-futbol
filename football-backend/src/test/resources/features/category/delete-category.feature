Feature: delete category
  Scenario: I cant see deleted category
    Given I have a category
    When I delete the category
    Then I dont see the category anymore