Feature: Subtraction

  Scenario: Subtract single
    Given I have two numbers that are 4 and 6
    When I ask the service to 'subtract' them
    Then the result should be -2