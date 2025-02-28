package endpoints;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import playload.User;
import utilities.ExtentReportManager;
import utilities.RestAssuredLogger;
import static io.restassured.RestAssured.*;

//created to perform crud operations methods
public class userEndpoints {

    public static Response createUsers(User playload){
       Response response = given()
               .filter(new RequestLoggingFilter(ExtentReportManager.getRequestPrintStream()))
               .filter(new ResponseLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(playload)
                .when()
                .post(Routes.post_url);
        return response;
    }

    public static Response getSpecificUser(int userId){
        Response response = given()
                .filter(new RequestLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .filter(new ResponseLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .pathParam("userId",userId)
                .get(Routes.get_url);
        return response;
    }

    public static Response updateTheUser(int userId, User playload){
        Response response = given()
                .filter(new RequestLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .filter(new ResponseLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON )
                .body(playload)
                .pathParam("userId",userId)
                .put(Routes.put_url);
        return response;
    }

    public static Response deleteUser(int userId){
        Response response = given()
                .filter(new RequestLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .filter(new ResponseLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .pathParam("userId",userId)
                .delete(Routes.delete_url);
        return response;
    }

    public static Response listUserBasedOnPage(int pageNumber){
        Response response = given()
                .filter(new RequestLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .filter(new ResponseLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .queryParam("page", pageNumber)
                .get(Routes.post_url);
        return  response;
    }

    public static Response UserDoesNotExist(int userId){
        Response response = given()
                .filter(new RequestLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .filter(new ResponseLoggingFilter(ExtentReportManager.getRequestPrintStream()))
                .pathParam("userId",userId)
                .get(Routes.get_url);
        return response;
    }
}
