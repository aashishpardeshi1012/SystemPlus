@brightHorizon
  Feature: Verify functionality of Bright Horizon

@TestSearchFunctionality
Scenario: Verify search functionality working or not
  Given Launch browser and open application
  And I click on search icon
  Then I see search field is visible
  And I search for "Employee Education in 2018: Strategies to Watch"
  Then I see search result "Employee Education in 2018: Strategies to Watch" at place "1"

@verifyFindCenter
Scenario: Verify find a center functionlity working
  Given Launch browser and open application
  And I click on "Find a Center" button
  Then I see url contain "child-care-locator"
  And I enter "New York" into field and press Enter
  Then I see result number and list number are same
  And I click on center "Bright Horizons at TriBeCa"
  Then I see "name" are same on list and popup
  Then I see "address" are same on list and popup

