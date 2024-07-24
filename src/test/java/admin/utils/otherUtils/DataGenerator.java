package admin.utils.otherUtils;

import lombok.Getter;

import java.security.SecureRandom;

public class DataGenerator {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*";
    private static final String FIRST_CHAR_LOGIN = UPPER + "_";
    private static final String FIRST_CHAR_PASSWORD = UPPER + LOWER;
    private static final String ALL_PASSWORD = UPPER + LOWER + DIGITS + SYMBOLS;
    private static final String ALL_LOGIN = UPPER + DIGITS + "_";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 25;

    @Getter
    private static String password;
    @Getter
    private static String login;

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

        DataGenerator.password = passwordBuilder.charAt(0) + new String(passwordArray);
        return DataGenerator.password;
    }

    public static String generateLogin() {
        int length = RANDOM.nextInt(MAX_LENGTH - MIN_LENGTH + 1) + MIN_LENGTH;
        StringBuilder loginBuilder = new StringBuilder(length);

        loginBuilder.append(FIRST_CHAR_LOGIN.charAt(RANDOM.nextInt(FIRST_CHAR_LOGIN.length())));
        loginBuilder.append(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
        loginBuilder.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));

        for (int i = 3; i < length; i++) { // Начинаем с 3, так как уже добавили 3 символа
            loginBuilder.append(ALL_LOGIN.charAt(RANDOM.nextInt(ALL_LOGIN.length())));
        }

        DataGenerator.login = loginBuilder.toString();
        return DataGenerator.login;
    }
}
