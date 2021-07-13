package Project;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GitHubProject {
	// Declare request specification
	RequestSpecification requestSpec;
	//Declare Variables
	String sshkey;
	int sshkeyId;
	
	@BeforeClass
	public void setUp() {
		// Create request specification
	    requestSpec = new RequestSpecBuilder()
	        .setContentType(ContentType.JSON) //set content
	        .addHeader("Authorization", "token ") // specifying token
	        .setBaseUri("https://api.github.com") // specifying base url
	        .build(); // build request
	    
	    sshkey="ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCwrO3x+Cfe+gOcemAjPt8PZXFIbueAOfy5SA3Jol0ucpDe95AH4ZwTo3p1//++TpxbrFjohNYCwYbnzKbdgBQUb++SiSLIAhR5p2Ht1rjLqLZ83dDCgOjN";
	}	
	@Test(priority=1)
	 public void addKey() {
	    String postReq = "{\"title\": \"TestAPIKey\", \"key\": \""+sshkey+"\"}";
		Response response = given().spec(requestSpec) // Use requestSpec
                .body(postReq) // Send request body
                .when().post("/user/keys"); // Send POST request
        
        // Print response of POST request
	    String body = response.getBody().asPrettyString();
	    System.out.println(body);
	    //Get sshid
	    sshkeyId=response.then().extract().path("id");
	    //Assertion
	    response.then().statusCode(201);
	    }
	@Test(priority=2)
	 public void GetKey() {
	    Response response = given().spec(requestSpec) // Use requestSpec
               .when().get("/user/keys"); // Send GET request
	    //print in console
	    String body = response.getBody().asPrettyString();
	    System.out.println(body);
	    //Assertion
	    response.then().statusCode(200);
	}	
	@Test(priority=3)
	 public void DeleteKey() {
		Response response = given().spec(requestSpec) // Use requestSpec
	    		.when().pathParam("keyid", sshkeyId).delete("/user/keys/{keyid}"); // Send Delete request"
		
		Reporter.log(response.getBody().asPrettyString());
	    //Assertion
	    response.then().statusCode(204);
	}	
        
}
