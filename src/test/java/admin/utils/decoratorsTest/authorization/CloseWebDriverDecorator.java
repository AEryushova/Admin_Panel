package admin.utils.decoratorsTest.authorization;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class CloseWebDriverDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Selenide.closeWebDriver();
    }


}
