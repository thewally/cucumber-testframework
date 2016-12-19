Feature: As Tester I want to send a service call and get response
  Scenario: SOAP service
    Given nothing
    When send request to emailverify service
    |email|arjen.vanderwal@gmail.com|
    Then get request
    Then get response
    Then check request headers
    Then check response headers
    Then check response by xpath
    Then node ResponseText contains value Mail Server will accept email
    Then node VerifyEmailResult contains 4 childs
    Then check response fields
    |ResponseText   |Mail Server will accept email|
    |ResponseCode   |3                            |
    |LastMailServer |gmail-smtp-in.l.google.com   |
    |GoodEmail      |true                         |

  Scenario: Http Get Request
    Given nothing
    When send get request
    Then get get response

  Scenario: Http Post Request
    Given nothing
    When send post request
    Then get post response