package admin.data;

import lombok.Data;
import lombok.Getter;

import java.io.File;

public class TestData {

    @Data
    public static class UserData {
        public static final String LOGIN_SUPER_ADMIN = "SUPER_ADMIN";
        public static final String PASSWORD_SUPER_ADMIN = "Qqqq123#";
        public static final String LOGIN_ADMIN = "ADMIN_TESTUI";
        public static final String PASSWORD_ADMIN = "WWqq123456!";
    }


    public static class DataTest {
        @Getter
        public static final String NEW_PASSWORD_ADMIN = "IIff123456$";
        @Getter
        public static final String LOGIN_ADMIN_TEST = "ADMIN_TESTUI_2";
        @Getter
        public static final String PASSWORD_ADMIN_TEST = "QQss123456*";
        @Getter
        public static final String NEW_PASSWORD_ADMIN_TEST = "RRjj123456#";
        @Getter
        public static final String DOCTOR = "Fer3 Не ТРОГАТЬ";
        @Getter
        public static final String DOCTOR_SPECIALIZATION = "врач-оториноларинголог";
        @Getter
        public static final String DEFAULT_PHOTO = "https://lk.mdapp.online/api/storage/img-2a680928-03b0-4ccb-ae99-9be7b4b879c0.jpg";
        @Getter
        public static File PHOTO = new File("src/test/resources/Photo 3,7mbJpeg.jpg");
        @Getter
        public static File LOGO = new File("src/test/resources/logo.png");
        @Getter
        public static final String MESSAGE_BUG_REPORT = "Не могу записаться к врачу";
        @Getter
        public static final String EMAIL_PATIENT = "Test@mail.ru";
        @Getter
        public static final String NAME_PATIENT = "Федоров Федор Федорович";
        @Getter
        public static final String QUESTION = "Как вернуть деньги?";
        @Getter
        public static final String ANSWER = "Никак";
        @Getter
        public static final String CHANGE_QUESTION = "Как записаться к врачу?";
        @Getter
        public static final String CHANGE_ANSWER = "Записаться к врачу можно через вкладку 'Врачи'";
        @Getter
        public static final String SECTION = "Образование";
        @Getter
        public static final String DESCRIPTION = "Июнь 2008, Высшее образование - специалитет 'Лечебное дело''";
        @Getter
        public static final String NEW_SECTION = "Сертификаты";
        @Getter
        public static final String NEW_DESCRIPTION = "Оториноларингология сертификат № 0163180786836";
        @Getter
        public static final String FEEDBACK = "Очень хороший врач";
        @Getter
        public static final String NEW_FEEDBACK = "Прием прошел замечательно";
        @Getter
        public static final String CATEGORY_RULES = "Диагностика";
        @Getter
        public static final String RULE_TITLE = "Это заголовок правила";
        @Getter
        public static final String RULE_DESCRIPTION = "Это само правило";
        @Getter
        public static final String NEW_RULE_TITLE = "Измененный заголовок";
        @Getter
        public static final String NEW_RULE_DESCRIPTION = "Измененное правило";
        @Getter
        public static final String NAME_CATEGORY = "Категория";
        @Getter
        public static final String NAME_SECTION = "Раздел для категории";
        @Getter
        public static final String NEW_NAME_SECTION = "Измененный раздел";
        @Getter
        public static final String NAME_SUBSECTION = "Подаздел для категории";
    }

    public static class DataSearch {
        @Getter
        public static final String DOCTOR_NAME_SEARCH = "надежда";
        @Getter
        public static final String DOCTOR_SPECIALIZATION_SEARCH = "терапевт";
        @Getter
        public static final String FAQ_SEARCH = "врач";
        @Getter
        public static final String SEARCH_BY_INCLUSION_DOCTORS = "етск";
        @Getter
        public static final String SEARCH_BY_INCLUSION_FAQ = "пис";
        @Getter
        public static final String DOCTOR_NAME_HIGH_REGISTER = "НAДЕЖДА";
        @Getter
        public static final String DOCTOR_NAME_DIFFERENT_REGISTER = "НаДеЖдА";
        @Getter
        public static final String FAQ_HIGH_REGISTER = "ВРАЧ";
        @Getter
        public static final String FAQ_DIFFERENT_REGISTER = "ВрАч";
    }
}
