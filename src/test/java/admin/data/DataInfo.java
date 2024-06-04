package admin.data;

import lombok.Getter;

public class DataInfo {

    public static class UserData {
        @Getter
        public static final String loginSuperAdmin = "SUPER_ADMIN";
        @Getter
        public static final String passwordSuperAdmin = "Qqqq123#";
        @Getter
        public static final String loginAdmin = "ADMIN_TESTUI";
        @Getter
        public static final String passwordAdmin = "WWqq123456!";
        @Getter
        public static final String loginAdminTest = "ADMIN_TESTUI_2";
        @Getter
        public static final String passwordAdminTest = "QQss123456*";
        @Getter
        public static final String newPasswordAdminTest = "RRjj123456#";

    }

    public static class Urls {
        @Getter
        public static final String uriAdminPanel = "http://192.168.6.48:8083";
        @Getter
        public static final String domain = "192.168.6.48";
        @Getter
        public static final String uriPersonalArea = "https://lk.mdapp.online";
        @Getter
        public static final String environmentFreeze = "freeze";
        @Getter
        public static final String environmentStage = "stg";


    }

    public static class DataTest {

        @Getter
        public static final String doctorName = "Fer3 Копия Тест";
        @Getter
        public static final String doctorId = "dc809290-b921-11ee-b697-77fc98f6d7d7";
        @Getter
        public static final String defaultPhoto = "https://lk.mdapp.online/api/storage/img-2a680928-03b0-4ccb-ae99-9be7b4b879c0.jpg";
        @Getter
        public static final String photo = "src/test/resources/Photo 3,7mbJpeg.jpg";
    }
}
