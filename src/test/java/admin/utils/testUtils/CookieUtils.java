package admin.utils.testUtils;
import admin.data.DataInfo;
import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;
import org.openqa.selenium.Cookie;

import java.io.*;
import java.util.Set;

public class CookieUtils {
    private static final String COOKIE_FILE = "cookies.txt";
    private static final String COOKIE_NAME = "token";
    private static final String COOKIE_DOMAIN = getIPFromUrl(DataInfo.Urls.getUriAdminPanel());
    @Getter
    private static String token;

    private static String getIPFromUrl(String url) {
        int startIndex = url.indexOf("//") + 2;
        int endIndex = url.indexOf(":", startIndex);
        if (endIndex == -1) {
            endIndex = url.length();
        }
        return url.substring(startIndex, endIndex);
    }

    public static void saveCookies() {
        Set<Cookie> cookies = WebDriverRunner.getWebDriver().manage().getCookies();
        try (FileWriter writer = new FileWriter(COOKIE_FILE)) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_NAME) && cookie.getDomain().equals(COOKIE_DOMAIN)) {
                    writer.write(cookie.getName() + "=" + cookie.getValue() + ";" + cookie.getDomain() + "\n");
                    token = cookie.getValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCookies() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COOKIE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String[] nameParts = parts[0].split("=");
                String name = nameParts[0];
                String value = nameParts[1];
                String domain = parts[1];
                if (name.equals(COOKIE_NAME) && domain.equals(COOKIE_DOMAIN)) {
                    Cookie cookie = new Cookie.Builder(name, value)
                            .domain(domain)
                            .build();
                    WebDriverRunner.getWebDriver().manage().addCookie(cookie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}