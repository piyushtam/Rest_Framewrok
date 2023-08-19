package setUp;

import static io.restassured.RestAssured.given;

import java.lang.reflect.Method;

import org.aeonbits.owner.ConfigFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import testUtil.ConfigProperty;

public class APISetUp {

	public static ConfigProperty configProperty;
	
	@BeforeSuite
	public void beforeSuite() {
		
		configProperty = ConfigFactory.create(ConfigProperty.class);
		
		RestAssured.baseURI = configProperty.getBaseURI();
		RestAssured.basePath = configProperty.getBasePath();
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		
		System.out.println("Test: " + method.getName()+ " execution has been started.");
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		
		if(result.getStatus() == ITestResult.SUCCESS)
			System.out.println("This TC is passed.");
		else if(result.getStatus() == ITestResult.FAILURE)
			System.out.println("This TC is failed.");
		else if(result.getStatus() == ITestResult.SKIP)
			System.out.println("This TC is skipped.");
	}	
	
	@AfterSuite
	public void afterSuite() {
		
		configProperty = ConfigFactory.create(ConfigProperty.class);
		
		RestAssured.baseURI = configProperty.getBaseURI();
		RestAssured.basePath = configProperty.getBasePath();
	}
	
	public RequestSpecification setRequestSpecification() {
		
		RequestSpecification spec = given().auth().basic(configProperty.getSecretKey(), "");
		return spec;
	}
}
