Feature: Create User

  Background:
    Given UP Service is up and running
    And User Endpoint is available:
      | key      | value  |
      | method   | POST   |
      | endpoint | /users |

  @create_successfully
  Scenario: create User successfully
    When a client wants to create a user:
      | key                     | value                |
      | id                      | 1                    |
      | firstName               | John                 |
      | lastName                | Doe                  |
      | email                   | john.doe@example.com |
      | userDetails.id          | 1                    |
      | userDetails.telegramId  | @johndoe             |
      | userDetails.mobilePhone | +1 555-555-5555      |
      | userDetails.zoneId      | America/New_York     |
    Then response code is 201
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

  @invalid_parameters
  Scenario: client error while creating a user with invalid parameters
    When a client wants to create a user:
      | key                     | value            |
      | id                      | 1                |
      | firstName               | John             |
      | lastName                | Doe              |
      | email                   | testEmailxxx.com |
      | userDetails.id          | 1                |
      | userDetails.telegramId  | @johndoe         |
      | userDetails.mobilePhone | +1 555-555-5555  |
      | userDetails.zoneId      | America/New_York |
    Then response code is 400
    And response body contains error:
      | key     | value                          |
      | status  | 400                            |
      | message | Validation failed for argument |
