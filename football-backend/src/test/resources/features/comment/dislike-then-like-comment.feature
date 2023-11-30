Feature: Disliking a comment and then liking it

  Scenario: I leave a dislike under a comment and then I remove it and give a like
    Given I have an article with a comment
    Given I dislike a comment
    And I undo leaving a dislike to a comment
    When I like a comment
    Then The comment gets one less dislike
    Then The comment gets one more like
