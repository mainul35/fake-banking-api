Feature: Deposit in account

  Scenario: deposit money in a customer's bank account
    Given a customer's account has been selected
    And selected account has balance of "300.00"
    When deposit "200.221" in the account
    Then the newly updated balance of the account should be "500.22"