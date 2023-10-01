Feature: Rest API functionalities

  Scenario: User able to add and remove book
    Given A list of authors are available
    When I add an author to my list
    Then The author is added
    When I get a specific author
    Then The specific author is available
    When I edit an author from my list
    Then The author content is edited
    When I remove author from my list
    Then The author is removed
