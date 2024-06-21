package admin.utils.preparationDataTests.authorization;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.codeborne.selenide.Selenide.localStorage;

public class CloseWebDriverDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Selenide.clearBrowserCookies();
        localStorage().removeItem("accessToken");
    }
}
