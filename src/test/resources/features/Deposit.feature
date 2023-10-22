Feature: Deposit in account

  Scenario: deposit money in a customer's bank account
    Given the customer's account has been selected
    And the customer with selected account has balance of "300.00"
    When deposit "200.00" in the account
    Then the new balance of the account should be "500.00"