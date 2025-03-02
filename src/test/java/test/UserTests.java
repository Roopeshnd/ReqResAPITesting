package test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import com.github.javafaker.Faker;
import endpoints.userEndpoints;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import playload.User;
import utilities.LoggerUtility;
import utilities.RetryAnalyzer;


public class UserTests {
    Faker faker = new Faker();
    String randomName = faker.name().fullName();
    String randomJob = faker.job().title();
    User userplayload;
    Logger logger;

    @BeforeMethod(onlyForGroups = "beforeTest")
    public void setup(){
        userplayload = new User();
        userplayload.setName(randomName);
        userplayload.setJob(randomJob);
        logger= LogManager.getLogger(this.getClass());
    }

    @Test(groups = "beforeTest",retryAnalyzer = RetryAnalyzer.class)
    public void CreateUserTest(){
        LoggerUtility.info("Starting API Test: CreateUser");
        Response response = userEndpoints.createUsers(userplayload);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),201);
        LoggerUtility.info("API Test Passed: CreateUser");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void getSpecificUserTest(){
        logger.info("Starting API Test: getSpecificUserTest");
        Response response = userEndpoints.getSpecificUser(2);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200);
        LoggerUtility.info("API Test Passed: getSpecificUserTest");
    }

    @Test(groups = "beforeTest",retryAnalyzer = RetryAnalyzer.class)
    public void updateUserTest(){
        LoggerUtility.info("Starting API Test: updateUserTest");
        Response response = userEndpoints.updateTheUser(2,userplayload);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200);
        LoggerUtility.info("API Test Passed: updateUserTest");
        Assert.fail();
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void UserDoesNotExitTest(){
        LoggerUtility.info("Starting API Test: UserDoesNotExitTest");
        Response response = userEndpoints.UserDoesNotExist(23);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),404);
        LoggerUtility.info("API Test Passed: UserDoesNotExitTest");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void listUserBasedOnPage(){
        LoggerUtility.info("Starting API Test: listUserBasedOnPage");
        Response response = userEndpoints.listUserBasedOnPage(2);
        response.then().log().all();
        Assert.assertEquals(response.statusCode(),200);
        LoggerUtility.info("API Test Passed: listUserBasedOnPage");
    }

}
