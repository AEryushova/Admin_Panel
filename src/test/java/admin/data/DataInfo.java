package admin.data;

import lombok.Getter;

import java.io.File;

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
        public static final String newPasswordAdmin = "IIff123456$";
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
        public static final String doctorName = "Fer3 Не ТРОГАТЬ";
        @Getter
        public static final String doctorSpecialization = "врач-оториноларинголог";
        @Getter
        public static final String doctorId = "dc809290-b921-11ee-b697-77fc98f6d7d7";
        @Getter
        public static final String defaultPhoto = "https://lk.mdapp.online/api/storage/img-2a680928-03b0-4ccb-ae99-9be7b4b879c0.jpg";
        @Getter
        public static File photo = new File("src/test/resources/Photo 3,7mbJpeg.jpg");
        @Getter
        public static final String messageBugReport = "Не могу записаться к врачу";
        @Getter
        public static final String emailPatient = "Test@mail.ru";
        @Getter
        public static final String namePatient = "Федоров Федор Федорович";
        @Getter
        public static final String question = "Как вернуть деньги?";
        @Getter
        public static final String answer = "Никак";
        @Getter
        public static final String changeQuestion = "Как записаться к врачу?";
        @Getter
        public static final String changeAnswer = "Записаться к врачу можно через вкладку 'Врачи'";
        @Getter
        public static final String section= "Образование";
        @Getter
        public static final String description = "Июнь 2008, Высшее образование - специалитет, магистратура по специальности: \"Лечебное дело\"";
        @Getter
        public static final String newSection= "Сертификаты";
        @Getter
        public static final String newDescription = "Оториноларингология сертификат № 0163180786836";
        @Getter
        public static final String feedback = "Очень хороший врач";
    }
}
