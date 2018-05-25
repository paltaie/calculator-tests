Feature: Subtraction

  Scenario: Subtract single
    Given I have two numbers that are 4 and 6
    When I ask the service to 'subtract' them
    Then the result should be -2

  Scenario: Subtraction in batch
    Given I have the following pairs of numbers to subtract:
      | 4 | 4 |
      | 5 | 6 |
      | 6 | 7 |
    When I ask the service to 'subtract' them in batch
    Then the results should be:
      | 0  |
      | -1 |
      | -1 |