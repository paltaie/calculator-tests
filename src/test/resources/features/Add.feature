Feature: Addition

  Scenario: Simple addition of two numbers
    Given I have two numbers that are 3 and 2
    When I ask the service to 'add' them
    Then the result should be 5