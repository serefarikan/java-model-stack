Feature: Parse ODIN files
  As an external system
  In order to share object contents accross different software
  I want to serialise and deserialise objects into a standard textual representation

  Scenario: Read an ODIN file to memory
    Given An ODIN file exists on the disk
    When I load the file to parser
    And parse the file contents
    Then I should have a representation of the file contents in memory in ODIN form