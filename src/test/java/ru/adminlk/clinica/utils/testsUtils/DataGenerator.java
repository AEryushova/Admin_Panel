package ru.adminlk.clinica.utils.testsUtils;

import com.github.javafaker.Faker;
import ru.adminlk.clinica.data.GeneratedTestData;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {

    private static final Faker FAKER_RU = new Faker(new Locale("ru"));
    private static final Faker FAKER_ENG = new Faker(Locale.ENGLISH);

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
        GeneratedTestData.setPassword(generatedPassword);
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
        GeneratedTestData.setLogin(generateLogin);
        return generateLogin;
    }

    public static String generateEmail() {
        String email= FAKER_ENG.internet().emailAddress();
        GeneratedTestData.setEmail(email);
        return email;
    }

    public static String generateText() {
        String text= FAKER_RU.address().streetAddress();
        GeneratedTestData.setText(text);
        return text;
    }

    public static String generateWord() {
        String word= FAKER_RU.name().lastName();
        GeneratedTestData.setWord(word);
        return word;
    }

    public static String generateNamePatient() {
        String name= FAKER_RU.name().fullName();
        GeneratedTestData.setNamePatient(name);
        return name;
    }

    public static String generateQuestion() {
        String sentence = FAKER_RU.company().name();
        String question=sentence.substring(0, 1).toUpperCase() + sentence.substring(1) + "?";
        GeneratedTestData.setQuestionFaq(question);
        return question;
    }

    public static String generateCategoryName() {
        String name= FAKER_RU.address().country();
        GeneratedTestData.setCategoryName(name);
        return name;
    }

    public static String generateSectionName() {
        String name= FAKER_RU.address().city();
        GeneratedTestData.setSectionName(name);
        return name;
    }

    public static String generateSubSectionName() {
        String name= FAKER_RU.address().streetName();
        GeneratedTestData.setSubSectionName(name);
        return name;
    }

    public static String generatePhone() {
        String phone = FAKER_RU.phoneNumber().phoneNumber();
        phone = phone.replaceAll("(\\+\\d)\\((\\d{3})\\)(\\d{3})", "$1 ($2) $3");
        String phoneDB = phone.replaceAll("[+\\-()\\s]", "");
        GeneratedTestData.setPhone(phone);
        return phoneDB;
    }

    public static String generateAgentId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        String agentId=sb.toString();
        GeneratedTestData.setAgentId(agentId);
        return agentId;
    }
}
