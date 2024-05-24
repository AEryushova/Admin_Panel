package user;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class AuthPage {

    private final SelenideElement authButton = $x("//div[text()='ПЕРЕЙТИ НА']");

    public HomePage authorizationLK() {
        authButton.click();
        return new HomePage();
    }
}
