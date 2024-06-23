package admin.utils.preparationDataTests.general;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class NotificationDecorator implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        closeNotification();
    }

    private void closeNotification() {
        BasePage basePage = Selenide.page(BasePage.class);
        if (basePage.isNotificationVisible()) {
            basePage.closeNotification();
        }
    }
}
