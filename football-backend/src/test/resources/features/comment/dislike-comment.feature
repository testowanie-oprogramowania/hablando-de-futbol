Feature: Disliking a comment

  Scenario: I leave a dislike under  comment
    Given I have an article with a comment
    When I dislike a comment
    Then The comment gets one more dislike

