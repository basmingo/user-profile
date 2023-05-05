Feature: Find User by ID

  Background:
    Given UP Service is up and running
    And User Endpoint is available:
      | key      | value       |
      | method   | GET         |
      | endpoint | /users/{id} |

#  @existing_user
  Scenario: Find existing user
    When a client wants to find a user with id: 1
    Then response code is 200
    And response body contains:
      | key                     | value                |
      | id                      | 1                    |
      | firstName               | John                 |
      | lastName                | Doe                  |
      | email                   | john.doe@example.com |
      | userDetails.id          | 1                    |
      | userDetails.telegramId  | @johndoe             |
      | userDetails.mobilePhone | +1 555-555-5555      |
      | userDetails.zoneId      | America/New_York     |

#  @nonexistent_user
  Scenario: User not found
    When a client wants to find a user with id: 10
    Then response code is 404
    And response body contains error:
      | key     | value                     |
      | status  | 404                       |
      | message | No user found with id: 10 |
      | details |                           |