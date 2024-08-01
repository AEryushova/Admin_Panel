package data;

import lombok.Data;
import lombok.Setter;


import java.io.File;

public class TestData {

    @Data
    public static class UserData {
        public static final String LOGIN_SUPER_ADMIN = "SUPER_ADMIN";
        public static final String PASSWORD_SUPER_ADMIN = "Qqqq123#";
        public static final String LOGIN_ADMIN = "ADMIN_TESTUI";
        public static final String PASSWORD_ADMIN = "WWqq123456!";
        public static final String USER_NAME_LK = "UPNBWPGQF7I";
    }

    @Data
    public static class DataTest {
        @Setter
        public static String login;
        @Setter
        public static String password;
        @Setter
        public static String text;
        @Setter
        public static String word;
        @Setter
        public static String email;
        @Setter
        public static String namePatient;
        @Setter
        public static String questionFaq;
        @Setter
        public static String categoryName;
        @Setter
        public static String sectionName;
        @Setter
        public static String subSectionName;

        public static final String DOCTOR = "Fer3 Не ТРОГАТЬ";
        public static final String DOCTOR_SURNAME = "Тестов";
        public static final String DOCTOR_SPECIALIZATION = "врач-оториноларинголог";
        public static final String DEFAULT_PHOTO = "https://lk.mdapp.online/api/storage/img-2a680928-03b0-4ccb-ae99-9be7b4b879c0.jpg";
        public static final File PHOTO = new File("src/test/resources/Photo 3,7mbJpeg.jpg");
        public static final File LOGO = new File("src/test/resources/logo.png");
        public static final String NAME_OTHER_SERVICE_CATEGORY = "Иные услуги";
    }

    @Data
    public static class DataSearch {
        public static final String DOCTOR_NAME_SEARCH = "надежда";
        public static final String DOCTOR_SPECIALIZATION_SEARCH = "терапевт";
        public static final String FAQ_SEARCH = "врач";
        public static final String SEARCH_BY_INCLUSION_DOCTORS = "етс";
        public static final String SEARCH_BY_INCLUSION_FAQ = "пис";
        public static final String DOCTOR_NAME_HIGH_REGISTER = "НAДЕЖДА";
        public static final String DOCTOR_NAME_DIFFERENT_REGISTER = "НаДеЖдА";
        public static final String FAQ_HIGH_REGISTER = "ВРАЧ";
        public static final String FAQ_DIFFERENT_REGISTER = "ВрАч";
    }
}
