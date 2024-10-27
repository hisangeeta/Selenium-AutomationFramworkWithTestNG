package LogTests;




import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class LogTests {
	Logger mylog;
	ExtentReports extent=null;
	ExtentSparkReporter spark= null;
	
@BeforeClass	
public void setup() {
mylog=LogManager.getLogger(LogTests.class);
extent=new ExtentReports();
spark = new ExtentSparkReporter("results/Spark.html");
spark.config().setDocumentTitle("Test Execution Report");
spark.config().setReportName("Salesforce testing");
spark.config().setTheme(Theme.DARK);
extent.setSystemInfo("Host Name","Salesforce");
extent.setSystemInfo("UserName", "Sangeeta");
extent.setSystemInfo("Environment", "QA");

extent.attachReporter(spark);

}


@AfterClass
public void tearDown() {
	
	extent.flush();
	
}


@Test
public void test1() {
	ExtentTest test=extent.createTest("test1");
	try {
	int a=10/0;
	//System.out.println("issue");
	mylog.info("test completed with success");// new version
	//mylog.log(Level.INFO, "test completed with success");// old version 
	
	//creating logs using extent report
	
	test.pass("test completed with success");
	test.log(Status.PASS, "test completed with success");//old version
}
	catch (ArithmeticException e) {
		//System.out.println("exception");
		mylog.error("test completed with failure");
		test.fail("test completed with failure");
		test.fail(e);
	}
	
	
}


@Test
public void test2() {
	//System.out.println("test completed");
	ExtentTest test=extent.createTest("test2");
	
	mylog.debug("test started");
	test.info("test started");
	
}



}
