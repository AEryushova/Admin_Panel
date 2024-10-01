package ru.adminlk.clinica.samsmu.data;

import lombok.Data;
import java.io.File;

@Data
public class FinalTestData {

    @Data
    public static class UserData {
        public static final String
                LOGIN_SUPER_ADMIN = "SUPER_ADMIN_ANNA",
                PASSWORD_SUPER_ADMIN = "Qqqq123#",
                LOGIN_ADMIN = "ADMIN_TESTUI",
                PASSWORD_ADMIN = "WWqq123456!";
    }

    @Data
    public static class TestData {
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
    public static class SearchData {
        public static final String
                DOCTOR_NAME_SEARCH = "Надежда",
                DOCTOR_SPECIALIZATION_SEARCH = "гинеколог",
                SEARCH_BY_INCLUSION_DOCTORS = "етс",
                FAQ_SEARCH = "Врач",
                SEARCH_BY_INCLUSION_FAQ = "пис";
    }
}
