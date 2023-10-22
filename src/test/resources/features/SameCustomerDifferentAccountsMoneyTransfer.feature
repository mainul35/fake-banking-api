Feature: Transfer money between accounts of same customers

  Scenario: transfer money from one customer account to another customer account
    Given the senderAccount has been selected
    And the recipientAccount has been selected
    And the customer with senderAccount has balance of "370.00"
    And the customer with recipientAccount has balance of "300.00"
    When transfer "25.27" to recipientAccount from senderAccount
    Then the new balance of the senderAccount should be "344.73"
    And the new balance of the recipientAccount should be "325.27"
