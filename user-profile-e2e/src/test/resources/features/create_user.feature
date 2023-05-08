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
      | key                     | value            |
      | id                      | 1                |
      | firstName               | Young            |
      | lastName                | Youngling        |
      | email                   | young@test.ru    |
      | userDetails.id          | 1                |
      | userDetails.telegramId  | @telegram        |
      | userDetails.mobilePhone | +111 222 333 444 |
      | userDetails.zoneId      | Europe/Paris     |
    Then response code is 201
    And response body contains:
      | key                     | value            |
      | id                      | 1                |
      | firstName               | Young            |
      | lastName                | Youngling        |
      | email                   | young@test.ru    |
      | userDetails.id          | 1                |
      | userDetails.telegramId  | @telegram        |
      | userDetails.mobilePhone | +111 222 333 444 |
      | userDetails.zoneId      | Europe/Paris     |

  @invalid_parameters
  Scenario: client error while creating a user with invalid parameters
    When a client wants to create a user:
      | key                     | value            |
      | id                      | 1                |
      | firstName               | Young            |
      | lastName                | Youngling        |
      | email                   | testEmailxxx.com |
      | userDetails.id          | 1                |
      | userDetails.telegramId  | @telegram        |
      | userDetails.mobilePhone | +111 636 856 789 |
      | userDetails.zoneId      | Europe/Paris     |
    Then response code is 400
    And response body contains error:
      | key     | value                          |
      | status  | 400                            |
      | message | Validation failed for argument |
