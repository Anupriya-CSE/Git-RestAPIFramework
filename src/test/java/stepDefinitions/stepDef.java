package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class stepDef {
	
	RequestSpecification reqspec;
	ResponseSpecification resspec;
	Response response;
	String responseString;
	static String place_ID;
	
	TestDataBuild data = new TestDataBuild();
	Utils util = new Utils();
	
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		reqspec = given().spec(util.requestSpecification()).body(data.addPlacePayload(name,language,address));
				
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		APIResources resourceAPI = APIResources.valueOf(resource); 
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if(method.equalsIgnoreCase("POST")) {
			response =reqspec.when().post(resourceAPI.getResource());
					
		}
		else if(method.equalsIgnoreCase("GET")) {
			response =reqspec.when().get(resourceAPI.getResource());
		}
	}
	
	@Then("API call got success with status code {int}")
	public void api_call_got_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);
		

	}
	@And("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		
		assertEquals(util.getJsonPath(response,keyValue), expectedValue);

	}
	
	@And("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		place_ID = util.getJsonPath(response, "place_id");
		reqspec = given().spec(util.requestSpecification()).queryParam("place_id", place_ID);
		user_calls_with_http_request(resource, "GET");
		String name = util.getJsonPath(response, "name");
		assertEquals(expectedName, name);
	}
	
	@Given("Delete Place Payload")
	public void delete_place_payload() throws IOException {
	   reqspec = given().spec(util.requestSpecification()).body(data.deletePlacePayload(place_ID));
	   
	}

}
