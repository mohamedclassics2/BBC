@regression
Feature: BBC RMS API Tests

  Background:
    Given user sends a GET request to "media" endpoint

#Task Scenario 1
  @wip
  Scenario: Check status code and response time
    Then response status code is 200
    Then response time is less than 3000 milliseconds

#  Task Scenario 2
  Scenario: Check id field is populated and segment_type is music
    Then id field is populated
    Then "segment_type" is always "music"

#  Task Scenario 3
  Scenario: Check primary field is not empty
    Then "primary" field is not empty

#  Task Scenario 4
  Scenario: Check now_playing
    Then only one track is playing

    #  Task Scenario 5
  Scenario: Check Date in Headers
    Then "Date" value is displayed in Headers



# Additional 3 Scenarios
#  Scenario: Check Synopsees
#    Then response body should contain "Synopses"
#
#  Scenario: Check start and end value
#    Then start and end value are displayed
#
#  Scenario: Check music label
#    Then music label should show "Spotify"
