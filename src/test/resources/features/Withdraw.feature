Feature: Withdraw from account

  Scenario: withdraw money from a customer's bank account
    Given the customer's account has been selected
    And the customer with selected account has balance of "430.22"
    When withdraw "50.884" from the account
    Then the new balance of the account should be "379.34"