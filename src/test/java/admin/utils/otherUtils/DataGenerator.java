package admin.utils.otherUtils;

import admin.data.TestData;
import com.github.javafaker.Faker;

import java.security.SecureRandom;
import java.util.Locale;

public class DataGenerator {

    private final static Faker faker = new Faker(new Locale("ru"));

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$^&*";
    private static final String FIRST_CHAR_LOGIN = UPPER + "_";
    private static final String FIRST_CHAR_PASSWORD = UPPER + LOWER;
    private static final String ALL_PASSWORD = UPPER + LOWER + DIGITS + SYMBOLS;
    private static final String ALL_LOGIN = UPPER + DIGITS + "_";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 15;


    public static String generatePassword() {
        int length = RANDOM.nextInt(MAX_LENGTH - MIN_LENGTH + 1) + MIN_LENGTH;
        StringBuilder passwordBuilder = new StringBuilder(length);

        passwordBuilder.append(FIRST_CHAR_PASSWORD.charAt(RANDOM.nextInt(FIRST_CHAR_PASSWORD.length())));
        passwordBuilder.append(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
        passwordBuilder.append(LOWER.charAt(RANDOM.nextInt(LOWER.length())));
        passwordBuilder.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
        passwordBuilder.append(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())));

        for (int i = 5; i < length; i++) {
            passwordBuilder.append(ALL_PASSWORD.charAt(RANDOM.nextInt(ALL_PASSWORD.length())));
        }

        char[] passwordArray = passwordBuilder.substring(1).toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = RANDOM.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temp;
        }

        String generatedPassword = passwordBuilder.charAt(0) + new String(passwordArray);
        TestData.DataTest.setPASSWORD_ADMIN_TEST(generatedPassword);
        return generatedPassword;
    }

    public static String generateLogin() {
        int length = RANDOM.nextInt(MAX_LENGTH - MIN_LENGTH + 1) + MIN_LENGTH;
        StringBuilder loginBuilder = new StringBuilder(length);

        loginBuilder.append(FIRST_CHAR_LOGIN.charAt(RANDOM.nextInt(FIRST_CHAR_LOGIN.length())));
        loginBuilder.append(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
        loginBuilder.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));

        for (int i = 3; i < length; i++) {
            loginBuilder.append(ALL_LOGIN.charAt(RANDOM.nextInt(ALL_LOGIN.length())));
        }
        String generateLogin = loginBuilder.toString();
        TestData.DataTest.setLOGIN_ADMIN_TEST(generateLogin);
        return generateLogin;
    }

    public static String generateEmail() {
        String email=faker.internet().emailAddress();
        TestData.DataTest.setEMAIL_PATIENT(email);
        return email;
    }

    public static String generateText() {
        String text=faker.lorem().sentence();
        TestData.DataTest.setTEXT(text);
        return text;
    }
}
