Feature: I can undo leaving a like to a comment

  Scenario: I like a comment and then undo it
    Given I have an article with a comment
    Given I like a comment
    When I undo leaving a like to a comment
    Then The comment gets one less like
