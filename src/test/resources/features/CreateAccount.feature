Feature: Create a new bank account

  Scenario: A customer creates a new bank account with an initial deposit
    Given A customer profile has been selected with id
    When the customer requests to create a new bank account with an initial deposit of "300.00"
    Then a new bank account should be created
    And the balance of the new account should be "300.00"
