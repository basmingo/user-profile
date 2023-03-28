Feature: the user can be retrieved
  Scenario: client makes call to POST /users
    When the client calls /users
    Then the client receives status code of 200
    And the client receives server version User
  Scenario: client makes call to GET /userProfile
    Given the client calls /userProfile
    When the client receives status code of 200
    Then the client receives server version User profile