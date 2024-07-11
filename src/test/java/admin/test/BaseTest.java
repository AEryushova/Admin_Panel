package admin.test;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest {

    @Step("Проверить отображение нотификации через 7 сек.")
    protected void checkCloseNotificationTimeout(BasePage basePage){
        Selenide.sleep(2000);
        assertTrue(basePage.isNotificationAppear());
        Selenide.sleep(7000);
        assertFalse(basePage.isNotificationAppear());
    }

    @Step("Закрыть нотификатию и проверить ее отображение")
    public void checkCloseNotification(BasePage basePage){
        Selenide.sleep(2000);
        assertTrue(basePage.isNotificationAppear());
        basePage.closeNotification();
        assertFalse(basePage.isNotificationAppear());
    }
}
