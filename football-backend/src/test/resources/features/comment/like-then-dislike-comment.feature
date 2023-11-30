Feature: Liking a comment and then disliking it

  Scenario: I leave a like under a comment and then I remove it and give a dislike
    Given I have an article with a comment
    Given I like a comment
    And I undo leaving a like to a comment
    When I dislike a comment
    Then The comment gets one more dislike
    Then The comment gets one less like
