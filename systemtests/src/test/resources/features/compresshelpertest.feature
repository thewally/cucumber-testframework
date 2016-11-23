Feature: As Tester I want to compress and decompress a file
  Scenario: Compress a file and check availability
    Given create file with directory dir and filename file.txt
    When compress file
    Then compressed file is available