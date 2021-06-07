package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		
		stepDef sd = new stepDef();
		if(stepDef.place_ID==null) {
			sd.add_place_payload_with("Anu", "Tamil", "Chennai");
			sd.user_calls_with_http_request("addPlaceAPI", "post");
			sd.verify_place_id_created_maps_to_using("Anu", "getPlaceAPI");
		}
		
	}

}
