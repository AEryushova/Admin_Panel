package admin.data;

import lombok.Data;


import java.io.File;

public class TestData {

    @Data
    public static class UserData {
        public static final String LOGIN_SUPER_ADMIN = "SUPER_ADMIN";
        public static final String PASSWORD_SUPER_ADMIN = "Qqqq123#";
        public static final String LOGIN_ADMIN = "ADMIN_TESTUI";
        public static final String PASSWORD_ADMIN = "WWqq123456!";
    }

    @Data
    public static class DataTest {
        public static final String NEW_PASSWORD_ADMIN = "IIff123456$";
        public static final String LOGIN_ADMIN_TEST = "ADMIN_TESTUI_2";
        public static final String PASSWORD_ADMIN_TEST = "QQss123456*";
        public static final String NEW_PASSWORD_ADMIN_TEST = "RRjj123456#";
        public static final String DOCTOR = "Fer3 Не ТРОГАТЬ";
        public static final String DOCTOR_SPECIALIZATION = "врач-оториноларинголог";
        public static final String DEFAULT_PHOTO = "https://lk.mdapp.online/api/storage/img-2a680928-03b0-4ccb-ae99-9be7b4b879c0.jpg";
        public static File PHOTO = new File("src/test/resources/Photo 3,7mbJpeg.jpg");
        public static File LOGO = new File("src/test/resources/logo.png");
        public static final String MESSAGE_BUG_REPORT = "Не могу записаться к врачу";
        public static final String EMAIL_PATIENT = "Test@mail.ru";
        public static final String NAME_PATIENT = "Федоров Федор Федорович";
        public static final String QUESTION = "Как вернуть деньги?";
        public static final String ANSWER = "Никак";
        public static final String CHANGE_QUESTION = "Как записаться к врачу?";
        public static final String CHANGE_ANSWER = "Записаться к врачу можно через вкладку 'Врачи'";
        public static final String SECTION = "Образование";
        public static final String DESCRIPTION = "Июнь 2008, Высшее образование - специалитет 'Лечебное дело''";
        public static final String NEW_SECTION = "Сертификаты";
        public static final String NEW_DESCRIPTION = "Оториноларингология сертификат № 0163180786836";
        public static final String FEEDBACK = "Очень хороший врач";
        public static final String NEW_FEEDBACK = "Прием прошел замечательно";
        public static final String CATEGORY_RULES = "Диагностика";
        public static final String RULE_TITLE = "Это заголовок правила";
        public static final String RULE_DESCRIPTION = "Это само правило";
        public static final String NEW_RULE_TITLE = "Измененный заголовок";
        public static final String NEW_RULE_DESCRIPTION = "Измененное правило";
        public static final String NAME_CATEGORY = "Категория";
        public static final String NAME_SECTION = "Раздел для категории";
        public static final String NEW_NAME_SECTION = "Измененный раздел";
        public static final String NAME_SUBSECTION = "Подраздел для категории";
        public static final String NEW_NAME_SUBSECTION = "Измененный подраздел";
        public static final String NAME_SERVICE = "Повязка Дезо";
    }

    @Data
    public static class DataSearch {
        public static final String DOCTOR_NAME_SEARCH = "надежда";
        public static final String DOCTOR_SPECIALIZATION_SEARCH = "терапевт";
        public static final String FAQ_SEARCH = "врач";
        public static final String SEARCH_BY_INCLUSION_DOCTORS = "етск";
        public static final String SEARCH_BY_INCLUSION_FAQ = "пис";
        public static final String DOCTOR_NAME_HIGH_REGISTER = "НAДЕЖДА";
        public static final String DOCTOR_NAME_DIFFERENT_REGISTER = "НаДеЖдА";
        public static final String FAQ_HIGH_REGISTER = "ВРАЧ";
        public static final String FAQ_DIFFERENT_REGISTER = "ВрАч";
    }
}
