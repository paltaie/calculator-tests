Feature: Addition

  Scenario: Simple addition of two numbers
    Given I have two numbers that are 3 and 2
    When I ask the service to 'add' them
    Then the result should be 5

  Scenario Outline: A few examples of addition
    Given I have two numbers that are <first number> and <second number>
    When I ask the service to 'add' them
    Then the result should be <the result>
    Examples:
      | first number | second number | the result |
      | 3            | 2             | 5          |
      | 4            | 9             | 13         |
      | 1000         | 2000          | 3000       |

  Scenario: Adding in batch
    Given I have the following pairs of numbers to add:
      | 4 | 4 |
      | 5 | 6 |
      | 6 | 7 |
    When I ask the service to 'add' them in batch
    Then the results should be:
      | 8  |
      | 11 |
      | 13 |