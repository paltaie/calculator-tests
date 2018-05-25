Feature: Multiplication

  Scenario: Multiply single
    Given I have two numbers that are 4 and 6
    When I ask the service to 'multiply' them
    Then the result should be 24

  Scenario: Multiplication in batch
    Given I have the following pairs of numbers to multiply:
      | 4 | 4 |
      | 5 | 6 |
      | 6 | 7 |
    When I ask the service to 'multiply' them in batch
    Then the results should be:
      | 16  |
      | 30 |
      | 42 |