Feature: delete article
  Scenario: I cant see deleted article
    Given I have article text prepared
    When I create an article
    When I delete the article
    Then I dont see the article anymore

