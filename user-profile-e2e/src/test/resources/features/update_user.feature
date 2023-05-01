Feature: Update User

  Background:
    Given UP Service is up and running
    And User Endpoint is available:
      | key      | value       |
      | method   | PUT         |
      | endpoint | /users/{id} |

  @update_successfully
  Scenario: Update User successfully
    When a client wants to update a user with id <1> to:
      | key                     | value                |
      | id                      | 1                    |
      | firstName               | John                 |
      | lastName                | Doe                  |
      | email                   | john.doe@example.com |
      | userDetails.id          | 1                    |
      | userDetails.telegramId  | @johndoe             |
      | userDetails.mobilePhone | +1 555-555-5555      |
      | userDetails.zoneId      | America/New_York     |
    Then response code is 200
    And response body contains:
      | key                     | value                 |
      | id                      | 1                     |
      | firstName               | Johnn                 |
      | lastName                | Doee                  |
      | email                   | john.doe1@example.com |
      | userDetails.id          | 1                     |
      | userDetails.telegramId  | @johndoe1             |
      | userDetails.mobilePhone | +1 555-555-5555       |
      | userDetails.zoneId      | America/New_York      |

  @invalid_parameters
  Scenario: Client error while updating a user with invalid parameters
    When a client wants to update a user with id <1> to:
      | key                     | value            |
      | id                      | 1                |
      | firstName               | John             |
      | lastName                | Doe              |
      | email                   | john.doe         |
      | userDetails.id          | 1                |
      | userDetails.telegramId  | @johndoe         |
      | userDetails.mobilePhone | +1 555-555-5555  |
      | userDetails.zoneId      | America/New_York |
    Then response code is 400
    And response body contains error:
      | key     | value                          |
      | status  | 400                            |
      | message | Validation failed for argument |

  @nonexistent_user
  Scenario: Client error while updating non-existing user
    When a client wants to update a user with id <999> to:
      | key                     | value                |
      | id                      | 999                  |
      | firstName               | John                 |
      | lastName                | Doe                  |
      | email                   | john.doe@example.com |
      | userDetails.id          | 1                    |
      | userDetails.telegramId  | @johndoe             |
      | userDetails.mobilePhone | +1 555-555-5555      |
      | userDetails.zoneId      | America/New_York     |
    Then response code is 404
    And response body contains error:
      | key     | value                      |
      | status  | 404                        |
      | message | No user found with id: 999 |