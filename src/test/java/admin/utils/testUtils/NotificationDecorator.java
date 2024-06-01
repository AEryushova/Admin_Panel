package admin.utils.testUtils;

import admin.pages.BasePage;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class NotificationDecorator implements AfterEachCallback {

    private BasePage basePage;

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        closeNotification();
    }

    private void closeNotification() {
        basePage = Selenide.page(BasePage.class);
        if (basePage.isNotificationVisible()) {
            basePage.closeNotification();
        }
    }
}
