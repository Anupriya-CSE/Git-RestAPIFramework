Feature: Validating Place API's

@AddPlace @Regression
Scenario Outline: Verify that Add Place is being successfully added using AddPlaceAPI
		Given Add Place Payload with "<name>" "<language>" "<address>"
		When user calls "addPlaceAPI" with "Post" http request
		Then API call got success with status code 200
		And "status" in response body is "OK"
		And "scope" in response body is "APP"
		And verify place_id created maps to "<name>" using "getPlaceAPI"
		Examples:
				|name 		| language | address						|
				|AAHouse	| English	 | World cross center	|
#				|BBHouse	| Tamil		 | Criss cross center	|
		
@DeletePlace @Regression
Scenario: Verify if Delete Place functionlaity is working
		Given Delete Place Payload
		When user calls "deletePlaceAPI" with "Post" http request
		Then API call got success with status code 200
		And "status" in response body is "OK"