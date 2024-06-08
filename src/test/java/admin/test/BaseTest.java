package admin.test;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Selenide;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest {


    protected void checkCloseNotificationTimeout(BasePage basePage){
        Selenide.sleep(2000);
        assertTrue(basePage.notificationAppear());
        Selenide.sleep(7000);
        assertFalse(basePage.notificationAppear());
    }

    public void checkCloseNotification(BasePage basePage){
        Selenide.sleep(2000);
        assertTrue(basePage.notificationAppear());
        basePage.closeNotification();
        assertFalse(basePage.notificationAppear());
    }
}
