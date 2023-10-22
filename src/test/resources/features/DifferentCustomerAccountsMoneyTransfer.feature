Feature: Transfer money between accounts

  Scenario: Customer transfers money from one account to another
    Given the senderAccount has been selected
    And the recipientAccount has been selected
    And the customer with senderAccount has balance of "300.00"
    And the recipient with recipientAccount has balance of "300.00"
    When transfer "70.00" to recipientAccount from senderAccount
    Then the new balance of the senderAccount should be "230.00"
    And the new balance of the recipientAccount should be "370.00"
