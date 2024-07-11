package admin.pages.SettingPage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class BugReport {

    private final SelenideElement AUTHOR = $x("//div[@class='CYEV']/span");
    private final SelenideElement EMAIL_AUTHOR = $x("//div[@class='A23y']/span");
    private final SelenideElement DATE_REPORT = $x("//div[@class='PEGd']/span");
    private final SelenideElement TEXT_REPORT = $x("//div[@class='fEEm']/span");
    private final SelenideElement DELETE_BUTTON = $x("//div[@class='wXIR']");

    @Step("Верифицировать сообщение об ошибке")
    public void verifyBugReport() {
        AUTHOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        EMAIL_AUTHOR.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DATE_REPORT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        TEXT_REPORT.shouldBe(Condition.visible, Duration.ofSeconds(5));
        DELETE_BUTTON.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    @Step("Получить автора сообщения об ошибке")
    public String getAuthorText() {
        AUTHOR.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return AUTHOR.getText();
    }

    @Step("Получить email автора сообщения об ошибке")
    public String getEmailAuthorText() {
        EMAIL_AUTHOR.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return EMAIL_AUTHOR.getText();
    }

    @Step("Получить дату сообщения об ошибке")
    public String getDateText() {
        DATE_REPORT.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return DATE_REPORT.getText();
    }

    @Step("Получить текст сообщения об ошибке")
    public String getReportText() {
        TEXT_REPORT.shouldBe(Condition.visible)
                .shouldBe(Condition.exist);
        return TEXT_REPORT.getText();
    }

    @Step("Нажать на кнопку удаления")
    public void clickButtonDeleteBugReport() {
        DELETE_BUTTON.shouldBe(Condition.visible)
                .shouldBe(Condition.enabled)
                .click();
    }


}
