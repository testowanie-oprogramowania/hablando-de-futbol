Feature: Delete comment
  As a user
  I want to delete my comment
  So that I can remove my comment from the article

  Scenario: I can delete my comment
    Given I have an article
    And I have a comment
    And I create a new comment
    When I delete the comment
    Then The comment gets deleted from the article
