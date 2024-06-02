package admin.utils.testUtils;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class AllureDecorator implements BeforeAllCallback, AfterAllCallback {


    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        SelenideLogger.removeListener("allure");
    }
}
