package admin.test;

import admin.data.DataTest;
import admin.pages.DoctorsPage;
import admin.pages.HeaderBar;
import admin.utils.CookieUtils;
import admin.utils.TestSetupAuthAdminPanel;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeaderBarTest {

    @BeforeAll
    static void setUpAllAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }


    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeAll
    static void setupAdminPanelWithCookies() {
        TestSetupAuthAdminPanel.authAdminPanel(DataTest.getLoginAdmin(), DataTest.getPasswordAdmin());
    }

    @BeforeEach
    void loadCookies() {
        TestSetupAuthAdminPanel.openAdministrationPage();
        CookieUtils.loadCookies();
    }

    @Feature("")
    @Story("Смена своего пароля админом")
    @Test
    void openDoctorsPage() {
        HeaderBar headerBar = new HeaderBar();
        headerBar.headerBarSuperAdmin();
        DoctorsPage doctorsPage = headerBar.doctorsTabOpen();
        doctorsPage.doctorsPage();
    }
}
