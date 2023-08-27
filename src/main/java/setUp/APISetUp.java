package setUp;

import static io.restassured.RestAssured.given;

import java.lang.reflect.Method;

import org.aeonbits.owner.ConfigFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import testUtil.ConfigProperty;
import testUtil.ExcelReader;
import testUtil.ExtentManager;

public class APISetUp {

	public static ConfigProperty configProperty;
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/testData/testdata.xlsx");
	public static ExtentReports extentReport;
	public static ThreadLocal<ExtentTest> classLevelLog = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> testLevelLog = new ThreadLocal<ExtentTest>();
	
	@BeforeSuite
	public void beforeSuite() {
		
		configProperty = ConfigFactory.create(ConfigProperty.class);		
		
		RestAssured.baseURI = configProperty.getBaseURI();
		RestAssured.basePath = configProperty.getBasePath();
		extentReport = ExtentManager.GetExtent(configProperty.getTestReportPath() + configProperty.getTestReportName());
	}
	
	@BeforeClass
	public void beforeClass() {
		//ExtentTest test = new ExtentTest(getClass().getSimpleName());
		ExtentTest classLevelTest = extentReport.createTest(getClass().getSimpleName());
		classLevelLog.set(classLevelTest);
	}
	
	@BeforeMethod
	public void beforeMethod(Method method) {
		
		ExtentTest test = classLevelLog.get().createNode(method.getName());
		classLevelLog.set(test);
		testLevelLog.get().info("Test: " + method.getName()+ " execution has been started.");
		//System.out.println("Test: " + method.getName()+ " execution has been started.");
	}
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		
		if(result.getStatus() == ITestResult.SUCCESS)
			testLevelLog.get().pass("This TC is passed.");
			//System.out.println("This TC is passed.");
		else if(result.getStatus() == ITestResult.FAILURE)
			testLevelLog.get().fail("This TC is failed.");
			//System.out.println("This TC is failed.");
		else if(result.getStatus() == ITestResult.SKIP)
			testLevelLog.get().skip("This TC is skipped.");
			//System.out.println("This TC is skipped.");
		
		extentReport.flush();
	}	
	
	@AfterSuite
	public void afterSuite() {
		
		configProperty = ConfigFactory.create(ConfigProperty.class);
		
		RestAssured.baseURI = configProperty.getBaseURI();
		RestAssured.basePath = configProperty.getBasePath();
	}
	
	public RequestSpecification setRequestSpecification() {
		
		RequestSpecification spec = given().auth().basic(configProperty.getSecretKey(), "");
		testLevelLog.get().info("Request Specification Set");
		return spec;
	}
}
