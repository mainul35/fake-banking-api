Feature: Transfer money between accounts of different customers

  Scenario: Customer transfers money from one account to another
    Given An account has been selected to be fromAccount
    And Another account has been selected to be toAccount
    And fromAccount has balance of "500.22"
    And toAccount has balance of "300.00"
    When transfer "70.00" to toAccount from fromAccount
    Then the new balance of the fromAccount should be "430.22"
    And the new balance of the toAccount should be "370.00"
