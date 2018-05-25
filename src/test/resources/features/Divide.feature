Feature: Division

  Scenario Outline:
    Given I have two numbers that are <numerator> and <denominator>
    When I ask the service to 'divide' them
    Then the result should be <the result>
    Examples:
      | numerator | denominator | the result |
      | 36        | 6           | 6          |

  Scenario: Dividing in batch
    Given I have the following pairs of numbers to divide:
      | 4   | 4  |
      | 100 | 10 |
      | 300 | 15 |
    When I ask the service to 'divide' them in batch
    Then the results should be:
      | 1  |
      | 10 |
      | 20 |