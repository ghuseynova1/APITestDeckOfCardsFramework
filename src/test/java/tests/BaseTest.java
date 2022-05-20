package tests;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.*;
import request.RequestFactory;
import utils.ConfigUtils;
import utils.ExtentReportUtils;

import java.util.Properties;


public class BaseTest implements AfterTestExecutionCallback, BeforeAllCallback, BeforeTestExecutionCallback,
AfterAllCallback{

     static String htmlReportFileName;
     static String configFileName;
     static ExtentReportUtils extentReport;
     static Properties configProperties;
     protected static RequestFactory requestFactory;
     String currentWorkingDirectory;


    @Override
    public void afterTestExecution(ExtensionContext extensionContext) throws Exception {

        if (!extensionContext.getExecutionException().isPresent()) {
            extentReport.addLog(Status.PASS,extensionContext.getDisplayName()+ " => passed");
        } else {
            extentReport.addLog(Status.FAIL,extensionContext.getDisplayName()+" => an error accured - "
                    +extensionContext.getExecutionException().get().getLocalizedMessage());
        }
    }


    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        currentWorkingDirectory = System.getProperty("user.dir");
        configFileName = currentWorkingDirectory + "/src/main/resources/config/config.properties";
        configProperties = ConfigUtils.readConfigProperties(configFileName);

        htmlReportFileName = currentWorkingDirectory + "/src/main/resources/reports/htmlReporter.html";
        extentReport = new ExtentReportUtils(htmlReportFileName);


        RestAssured.baseURI = configProperties.getProperty("baseUrl");


    }

    @BeforeAll
    public static void setup(){
        requestFactory = new RequestFactory();
        extentReport.createTestCase("Setup all updates");
        extentReport.addLog(Status.INFO, "Base url : "+RestAssured.baseURI);
    }


    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) throws Exception {
        extentReport.createTestCase(extensionContext.getDisplayName());
        extentReport.addLog(Status.INFO, extensionContext.getDisplayName()+" => started");
        extentReport.addLog(Status.INFO,"Url: "+RestAssured.baseURI+
                extensionContext.getDisplayName().split("-")[1].trim());
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        extentReport.closeReports();
    }


    @AfterAll
    public static void cleanUp(){
        RestAssured.reset();
    }

}
