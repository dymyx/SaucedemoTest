package base;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;

public class AllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object instance = result.getInstance();
        if (!(instance instanceof BaseTest test)) {
            return;
        }
        WebDriver driver = test.getDriver();
        if (driver == null) {
            return;
        }
        addScreenshot(driver);
        addPageSource(driver);
    }

    private void addScreenshot(WebDriver driver) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Скриншот при падении", "image/png",
                new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            System.err.println("Не удалось сделать скриншот: " + e.getMessage());
        }
    }

    private void addPageSource(WebDriver driver) {
        try {
            String pageSource = driver.getPageSource();
            Allure.addAttachment("Page Source при падении", "text/html",
                new ByteArrayInputStream(pageSource.getBytes()), "html");
        } catch (Exception e) {
            System.err.println("Не удалось получить page source: " + e.getMessage());
        }
    }
}
