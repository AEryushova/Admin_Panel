package data;

import lombok.Data;
import lombok.Setter;


import java.io.File;

public class TestData {

    @Data
    public static class UserData {
        public static final String
                LOGIN_SUPER_ADMIN = "SUPER_ADMIN",
                PASSWORD_SUPER_ADMIN = "Qqqq123#",
                LOGIN_ADMIN = "ADMIN_TESTUI",
                PASSWORD_ADMIN = "WWqq123456!";
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

        public static final String
                DOCTOR = "Тестов Стоматолог Самгму",
                DOCTOR_SURNAME = "Тестов",
                DOCTOR_SPECIALIZATION = "врач-стоматолог",
                DEFAULT_PHOTO = "https://lk.mdapp.online/api/storage/img-2a680928-03b0-4ccb-ae99-9be7b4b879c0.jpg",
                NAME_OTHER_SERVICE_CATEGORY = "Иные услуги";
        public static final File
                PHOTO = new File("src/test/resources/images/Photo 3,7mbJpeg.jpg"),
                LOGO = new File("src/test/resources/images/logo.png");
    }


    @Data
    public static class DataSearch {
        public static final String
                DOCTOR_NAME_SEARCH = "Надежда",
                DOCTOR_SPECIALIZATION_SEARCH = "гинеколог",
                SEARCH_BY_INCLUSION_DOCTORS = "етс",
                FAQ_SEARCH = "Врач",
                SEARCH_BY_INCLUSION_FAQ = "пис";
    }
}
