package admin.utils.otherUtils;

import lombok.Getter;

import java.security.SecureRandom;

public class DataGenerator {

        private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
        private static final String DIGITS = "0123456789";
        private static final String SYMBOLS = "!@#$%^&*";
        private static final String FIRST_CHAR = UPPER + LOWER + "_";
        private static final String ALL = UPPER + LOWER + DIGITS + SYMBOLS;
        private static final SecureRandom RANDOM = new SecureRandom();
        private static final int MIN_LENGTH = 8;
        private static final int MAX_LENGTH = 25;

        @Getter
        private static String password;

        public static String generatePassword() {
            int length = RANDOM.nextInt(MAX_LENGTH - MIN_LENGTH + 1) + MIN_LENGTH;
            StringBuilder password = new StringBuilder(length);

            password.append(FIRST_CHAR.charAt(RANDOM.nextInt(FIRST_CHAR.length())));
            password.append(UPPER.charAt(RANDOM.nextInt(UPPER.length())));
            password.append(LOWER.charAt(RANDOM.nextInt(LOWER.length())));
            password.append(DIGITS.charAt(RANDOM.nextInt(DIGITS.length())));
            password.append(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length())));

            for (int i = 5; i < length; i++) {
                password.append(ALL.charAt(RANDOM.nextInt(ALL.length())));
            }


            char[] passwordArray = password.substring(1).toCharArray();
            for (int i = 0; i < passwordArray.length; i++) {
                int randomIndex = RANDOM.nextInt(passwordArray.length);
                char temp = passwordArray[i];
                passwordArray[i] = passwordArray[randomIndex];
                passwordArray[randomIndex] = temp;
            }

            DataGenerator.password = password.charAt(0) + new String(passwordArray);
            return DataGenerator.password;
        }
    }
