package utilities;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import java.io.PrintStream;

public class RestAssuredLogger {
    public static PrintStream logStream;

    static {
        try {
            logStream = new PrintStream("./reports/RestAssuredLogs.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RequestLoggingFilter requestLogger = new RequestLoggingFilter(logStream);
    public static ResponseLoggingFilter responseLogger = new ResponseLoggingFilter(logStream);
}
