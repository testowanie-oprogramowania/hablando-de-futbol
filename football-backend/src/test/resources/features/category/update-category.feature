Feature: update category
  Scenario: I can see the newly updated category
    Given I have category data prepared
    Given I create new category
    When I prepared update category data
    When I update category
    Then I see updated category