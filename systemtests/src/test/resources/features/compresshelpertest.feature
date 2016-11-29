Feature: As Tester I want to compress and decompress a file
  Scenario: Compress a file and check availability
    Given cleanup working folder dir
    Given create file with directory dir and filename file.txt
    When compress file
    Then compressed file is available

  Scenario: Decompress a file and check availability
    Given select created compressed file with path dir and filename file.zip
    When decompress file to dir/tmp
    Then decompressed file file.txt is available in directory dir/tmp

  Scenario: service
    Given nothing
    When send request
    Then get response