package ru.adminlk.clinica.utils.preparationData.general;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Attachment;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import static org.openqa.selenium.logging.LogType.BROWSER;


public class AllureTestListeners implements BeforeAllCallback, AfterAllCallback, AfterTestExecutionCallback, TestWatcher {


    @Override
    public void beforeAll(ExtensionContext context) {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }


    @Override
    public void afterAll(ExtensionContext context) {
        SelenideLogger.removeListener("AllureSelenide");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            attachScreenshot();
            attachConsoleLogs();
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        attachScreenshot();
        attachConsoleLogs();
    }

    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "Screenshot", type = "image/png")
    private static byte[] attachScreenshot() {
        return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @SuppressWarnings("UnusedReturnValue")
    @Attachment(value = "Browser console logs", type="text/plain")
    private static String attachConsoleLogs(){
        return String.join("\n",Selenide.getWebDriverLogs(BROWSER));
    }
}



