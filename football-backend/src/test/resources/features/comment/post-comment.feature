Feature: Commenting on articles

  Scenario: I can see newly created comment under the article
    Given I have an article
    Given I have a comment
    When I create a new comment
    Then The comment gets added to the article
