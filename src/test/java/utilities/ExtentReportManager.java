package utilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {
    private static ExtentReports extent;
    private static ExtentSparkReporter sparkReporter;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private String reportFileName;

    // Stream to capture API logs
    private static ByteArrayOutputStream requestLogStream;
    private static PrintStream requestPrintStream;

    @Override
    public void onStart(ITestContext context) {
        // Delete old reports before creating a new one
        deleteOldReports("./reports");

        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportFileName = "TestReport-" + timestamp + ".html";

        sparkReporter = new ExtentSparkReporter("./reports/" + reportFileName);
        sparkReporter.config().setDocumentTitle("API Automation Report");
        sparkReporter.config().setReportName("Test Execution Report");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Tester", "Your Name");

        // Initialize request/response log streams
        requestLogStream = new ByteArrayOutputStream();
        requestPrintStream = new PrintStream(requestLogStream);
    }

    public static PrintStream getRequestPrintStream() {
        return requestPrintStream;
    }

    public static String getRequestLogs() {
        return requestLogStream.toString();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed ✅");
        test.get().log(Status.INFO, getRequestLogs());  // Attach API logs to report
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test Failed ❌");
        test.get().log(Status.FAIL, result.getThrowable().getMessage());
        test.get().log(Status.INFO, getRequestLogs());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped 🚧");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    // Method to delete old reports
    private void deleteOldReports(String folderPath) {
        File reportFolder = new File(folderPath);
        if (reportFolder.exists() && reportFolder.isDirectory()) {
            for (File file : reportFolder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".html")) {
                    file.delete(); // Delete old report
                }
            }
        }
    }
}