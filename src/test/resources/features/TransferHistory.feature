Feature: Retrieve transfer history for a given account

  Scenario: Fetch transfer history for a customer
    Given A customer's bank account has been selected
    When the account's transfer history is requested
    Then transfer history is fetched
