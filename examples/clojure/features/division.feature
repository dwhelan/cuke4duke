# language: en
Feature: Division
  In order to avoid silly mistakes
  Cashiers must be able to calculate a fraction

  Scenario: Regular numbers
    Given I have entered 3 into the calculator
    And I have entered 2 into the calculator
    When I press divide
    Then the current value should be 1.5

  # In order to make this pass we need a Before hook
  # that resets the calculator...
  #
  # Scenario: Regular numbers
  #   Given I have entered 7 into the calculator
  #   And I have entered 2 into the calculator
  #   When I press divide
  #   Then the current value should be 3.5
