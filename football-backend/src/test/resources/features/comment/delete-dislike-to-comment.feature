Feature: I can undo leaving a dislike to a comment

  Scenario: I dislike a comment and then undo it
    Given I have an article with a comment
    Given I dislike a comment
    When I undo leaving a dislike to a comment
    Then The comment gets one less dislike
