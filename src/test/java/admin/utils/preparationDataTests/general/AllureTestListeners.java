package admin.utils.preparationDataTests.general;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;



public class AllureTestListeners implements BeforeAllCallback, AfterAllCallback, AfterTestExecutionCallback, TestWatcher {


    @Override
    public void beforeAll(ExtensionContext context) {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }


    @Override
    public void afterAll(ExtensionContext context) {
        SelenideLogger.removeListener("AllureSelenide");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            try {
                attachScreenshot();
            } catch (Exception e) {
                System.err.println("Failed to attach screenshot to Allure report: " + e.getMessage());
            }
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] attachScreenshot() {
        return ((TakesScreenshot)WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        Allure.getLifecycle().addAttachment(
                "Screenshot", "image/png", "png", ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES));
    }
}



