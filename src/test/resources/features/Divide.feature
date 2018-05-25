Feature: Division

  Scenario Outline:
    Given I have two numbers that are <numerator> and <denominator>
    When I ask the service to 'divide' them
    Then the result should be <the result>
    Examples:
      | numerator | denominator | the result |
      | 36        | 6           | 6          |