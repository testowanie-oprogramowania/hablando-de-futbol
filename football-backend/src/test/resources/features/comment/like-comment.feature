Feature: Liking a comment

  Scenario: I leave a like under a comment
    Given I have an article with a comment
    When I like a comment
    Then The comment gets one more like

