package admin.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$x;

public class SettingPage {

    private final SelenideElement tabNameSetting = $x("//span[text()='Сообщения об ошибках']");
    private final SelenideElement bugReportAuthor = $x("//div[@class='CYEV']/span");
    private final SelenideElement bugReportEmailAuthor = $x("//div[@class='A23y']/span");
    private final SelenideElement bugReportDate = $x("//div[@class='PEGd']/span");
    private final SelenideElement bugReportText = $x("//div[@class='fEEm']/span");
    private final SelenideElement deleteBugReport = $x("//div[@class='wXIR']");
    private final SelenideElement notification=$x("//div[@role='alert']/div//following-sibling::div");
    private final SelenideElement closeNotification = $x("//button[@aria-label='close']");
    private final SelenideElement footerSettingPage = $x("//span[text()='@ Самарский государственный медицинский университет']");
    private final SelenideElement returnToStartButton = $x("//div[@class='_x1E']");


    public void settingPage() {
        tabNameSetting.shouldBe(Condition.visible, Duration.ofSeconds(5));
    }

    public String getTextByElement(String key) {
        Map<String, SelenideElement> elementMap = new HashMap<>();
        elementMap.put("author", bugReportAuthor);
        elementMap.put("emailAuthor", bugReportEmailAuthor);
        elementMap.put("date", bugReportDate);
        elementMap.put("text", bugReportText);
        SelenideElement element = elementMap.get(key);
            return element.getText();
    }

    public void deleteBugReport(){
        deleteBugReport.click();
    }

    public String getNotification() {
        notification.shouldBe(Condition.visible);
        return notification.getText();
    }

    public void closeNotification() {
        closeNotification.click();
        notification.shouldBe(Condition.hidden);
    }

    public void scrollPageToBottom() {
        footerSettingPage.scrollTo();
    }

    public void returnToStartPage() {
        returnToStartButton.shouldBe(Condition.visible, Duration.ofSeconds(5));
        returnToStartButton.click();
    }

    public void returnButtonDisappears(){
        returnToStartButton.shouldBe(Condition.hidden, Duration.ofSeconds(5));
    }
}
