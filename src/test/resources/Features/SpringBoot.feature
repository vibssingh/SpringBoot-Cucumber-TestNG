Feature: Verify springboot application using Cucumber and TestNG

  @ReceiveUserDetails
  Scenario Outline: Send a valid Request to get user details
    Given I send a request to the URL "/students" to get user details
    Then The response will return status 200 
    And The response contains id <studentID> and names "<studentNames>" and passport_no "<studentPassportNo>"

    Examples:
      |studentID    |studentNames  |studentPassportNo|
      |10001        |Annie         |E1234567         |
      |20001        |John          |A1234568         |
      |30001        |David         |C1232268         |
      |40001        |Amy           |D213458          |
      
   
  @CreateUser
  Scenario: Send a valid Request to create a user 
    Given I send a request to the URL "/students" to create a user with name "Annie" and passportNo "E1234567"
    Then The response will return status 201
    And Resend the request to the URL "/students" and the response returned contains name "Annie" and passport_no "E1234567"
