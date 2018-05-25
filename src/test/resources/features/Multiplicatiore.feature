Feature: Multiplication

  Scenario: Multiply single
    Given I have two numbers that are 4 and 6
    When I ask the service to 'multiply' them
    Then the result should be 24