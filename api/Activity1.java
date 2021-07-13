package Activities;

import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Activity1 {
	// Set Base URL
	final static String Base_URL= "https://petstore.swagger.io/v2/pet";
	
//POST case
		@Test(priority=1)
		public void AddNewPet() {
		    // Write the request body
		    String postReq = "{"
		            + "\"id\": 77232,"
		            + "\"name\": \"Riley\","
		            + " \"status\": \"alive\""
		        + "}";
		    
		    Response response = 
		        given().contentType(ContentType.JSON) // Set headers
		        .body(postReq)
		        .when().post(Base_URL); // Send POST request

		    // Print response of POST request
		    String body = response.getBody().asPrettyString();
		    System.out.println(body);
		    //Assertion
		    response.then().body("id", equalTo(77232));
		    response.then().body("name", equalTo("Riley"));
		    response.then().body("status", equalTo("alive"));
		    }	

 
//GET case
	@Test(priority=2)
	public void GetPetID() {
        Response response = 
                given().contentType(ContentType.JSON) // Set headers
                .when().get(Base_URL + "/77232"); // Send GET request
        
        // Print response of POST request
	    String body = response.getBody().asPrettyString();
	    System.out.println(body);
	    
	    // Assertion
	    response.then().body("id", equalTo(77232));
        response.then().body("name", equalTo("Riley"));
        response.then().body("status", equalTo("alive"));
        }
	//Delete case
	@Test(priority=3)
    public void deletePet() {
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .when().delete(Base_URL + "/77232"); // Send DELETE request
        // Print response
        System.out.println(response.getBody().asPrettyString());

        // Assertion
        response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("77232"));
    }
}
