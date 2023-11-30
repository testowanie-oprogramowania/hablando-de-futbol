Feature: post category
  Scenario: I can see newly created category
    Given I have category data prepared
    When I create new category
    When I list all categories
    Then I see new category
