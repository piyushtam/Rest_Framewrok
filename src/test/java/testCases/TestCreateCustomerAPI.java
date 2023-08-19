package testCases;


import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import setUp.APISetUp;

public class TestCreateCustomerAPI extends APISetUp {

	@Test
	public void validateCreateCustomerAPIWithValidData() {
		
		// Stripe APIs
		// baseURI: https://api.stripe.com/v1/customers
				 		
		RequestSpecification specification = setRequestSpecification().formParam("email", "pyush@gmail.com")
		.formParam("description", "REST API").formParam("name", "Piyush").log().all();
		
		Response resp = specification.post("https://api.stripe.com/v1/customers");
		resp.prettyPrint();
		
		Assert.assertEquals(200, resp.getStatusCode());
	}
}
