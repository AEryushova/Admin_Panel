package admin.pages.SettingPage;

import admin.pages.BasePage.BasePage;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class SettingPage extends BasePage {

    private final SelenideElement BUG_REPORTS_SECTION = $x("//span[text()='Раздел для мониторинга сообщений и жалоб пользователей ЛК']/parent::div/preceding-sibling::div/span");
    private final SelenideElement LOGO_APP_SECTION = $x("//span[text()='Логотип Приложения']");
    private final SelenideElement LOGO_APP = $x("//div[@class='D1Px']/img");
    private final SelenideElement EDIT_LOGO_BUTTON = $x("//div[@class='jUqF']/div");
    private final SelenideElement BUG_REPORT=$x("//div[@class='eYxe']");
    private final SelenideElement EMPTY_LIST_BUG_REPORT=$x("//span[text()='Нет сообщений от пользователей']");

    @Step("Верифицировать страницу Настройки")
    public void verifySettingPage() {
        BUG_REPORTS_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
        LOGO_APP_SECTION.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Получить сообщение об ошибке")
    public BugReport getBugReportCard(){
        BUG_REPORT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        return new BugReport();
    }

    @Step("Получить логотип")
    public SelenideElement getLogo() {
        return LOGO_APP;
    }

    @Step("Проверить отображение сообщения об ошибке")
    public boolean isExistsBugReport() {
        return BUG_REPORT.isDisplayed();
    }

    @Step("Нажать кнопку изменения логотипа")
    public EditLogoWindow clickButtonEditLogo() {
        EDIT_LOGO_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
        return new EditLogoWindow();
    }

    @Step("Получить высоту изображения логотипа")
    public int getHeightLogo(){
        return LOGO_APP.getSize().getHeight();
    }

    @Step("Проверить отображение информации о пустом списке сообщений об ошибках")
    public boolean isExistsEmptyList(){
        return EMPTY_LIST_BUG_REPORT.isDisplayed();
    }
}
