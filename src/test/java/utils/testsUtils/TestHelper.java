package utils.testsUtils;

import utils.APIUtils.PreparationDataSettingTest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

import static appData.AppData.URI_PERSONAL_AREA;


public class TestHelper {

    //Генерирует и возвращает ссылку на фото врача//
    public static String urlPhotoBuilder() {
        return URI_PERSONAL_AREA + PreparationDataSettingTest.getLocation();
    }

    //Генерирует и возвращает UUID для SQL-запросов//
    public static UUID generateUuid() {
        return UUID.randomUUID();
    }

    //Генерирует и возвращает дату и время в формате Timestamp для SQL-запросов//
    public static Timestamp getDateTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    //Генерирует старую дату ( - 2 месяца) и возвращает дату и время в формате Timestamp для SQL-запросов//
    public static Timestamp getPreviousMonthDateTime() {
        LocalDate previousMonthDate = LocalDate.now().minusMonths(2);
        return Timestamp.valueOf(previousMonthDate.atStartOfDay());
    }

    //Генерирует старую дату ( + 2 месяца) и возвращает дату и время в формате Timestamp для SQL-запросов//
    public static Timestamp getFutureMonthDateTime() {
        LocalDate futureMonthDate = LocalDate.now().plusMonths(2);
        return Timestamp.valueOf(futureMonthDate.atStartOfDay());
    }

    //Генерирует и возвращает дату и время - 1 день от текущей даты в формате Timestamp для SQL-запросов//
    public static Timestamp getPreviousDateTime() {
        LocalDateTime oneDaysAgo = LocalDateTime.now().minusDays(1);
        return Timestamp.valueOf(oneDaysAgo);
    }

    //Получает значение по ключу из json-объекта и возвращает его//
    public static String getValueFromJson(String json, String key) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(json, JsonArray.class);
        if (!jsonArray.isEmpty()) {
            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
            if (jsonObject.has(key)) {
                return jsonObject.get(key).getAsString();
            }
        }
        return null;
    }

    //Возвращает текущую дату в формате "17.06.2024"//
    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    //Возвращает текущую дату в русской локали в формате "01 Апреля 2024"//
    public static String getCurrentDateTextRu() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU")));
    }

    //Возвращает текущую дату в русской локали в формате "01 Апреля 2024 + г."//
    public static String getCurrentDateRuYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", new Locale("ru", "RU")));
    }

    //Возвращает название текущего месяца и год в русской локали в формате "апрель 2024"
    public static String getNameCurrentMonth() {
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("ru", "RU")));
        return formattedDate.toLowerCase();
    }

    //Возвращает название следующего месяца и год в русской локали в формате "май 2024"
    public static String getNameFutureMonth() {
        LocalDate nextMonthDate = LocalDate.now().plusMonths(1);
        return nextMonthDate.format(DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("ru", "RU"))).toLowerCase();
    }

    //Возвращает название предыдущего месяца и год в русской локали в формате "март 2024"//
    public static String getNamePreviousMonth() {
        LocalDate previousMonthDate = LocalDate.now().minusMonths(1);
        return previousMonthDate.format(DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("ru", "RU"))).toLowerCase();
    }

    //Возвращает название месяца 2 месяца назад и год в русской локали в формате "март 2024"//
    public static String getNameDoublePreviousMonth() {
        LocalDate previousMonthDate = LocalDate.now().minusMonths(2);
        return previousMonthDate.format(DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("ru", "RU"))).toLowerCase();
    }

    //Возвращает день недели и дату которая наступит через 2 дня от текущей даты в формате "Choose пятница, 1 ноября 2024 г."//
    public static String getDayCurrentMonthYear() {
        LocalDate futureDate = LocalDate.now().plusDays(2);
        String dayOfWeek = futureDate.format(DateTimeFormatter.ofPattern("EEEE", new Locale("ru")));
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", new Locale("ru")));
        return "Choose " + dayOfWeek + ", " + formattedDate;
    }

    //Возвращает день недели и дату которая наступит в следующем месяце + 2 дня в формате "Choose понедельник, 2 декабря 2024 г."//
    public static String getDayFutureMonth() {
        LocalDate futureDate = LocalDate.now().plusMonths(1).plusDays(2);
        String dayOfWeek = futureDate.format(DateTimeFormatter.ofPattern("EEEE", new Locale("ru")));
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", new Locale("ru")));
        return "Choose " + dayOfWeek + ", " + formattedDate;
    }

    //Возвращает день недели и дату которая наступила в предыдущем месяце в формате "Choose понедельник, 7 октября 2024 г."//
    public static String getDayPreviousMonth() {
        LocalDate futureDate = LocalDate.now().minusMonths(1).plusDays(2);
        String dayOfWeek = futureDate.format(DateTimeFormatter.ofPattern("EEEE", new Locale("ru")));
        String formattedDate = futureDate.format(DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", new Locale("ru")));
        return "Choose " + dayOfWeek + ", " + formattedDate;
    }

    //Возвращает дату, которая наступит через 2 дня от текущей даты в русской локали в формате "03 Апреля 2024"//
    public static String getFutureDateCurrentMonth() {
        LocalDate futureDate = LocalDate.now().plusDays(2);
        return futureDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU")));
    }

    //Возвращает дату, которая наступит через 2 дня от текущей даты в цифровом формате "03.04.2023"//
    public static String getActivationDateCurrentMonth() {
        LocalDate futureDate = LocalDate.now().plusDays(2);
        return futureDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    //Возвращает дату, которая наступит в следующем месяце + 2 дня от текущей даты в русской локали в формате "03 Май 2024"//
    public static String getNextMonthDate() {
        LocalDate futureDate = LocalDate.now().plusMonths(1).plusDays(2);
        return futureDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU")));
    }

    //Возвращает дату, вычесленную от текущей + 2 дня прошлого месяца в русской локали в формате "03 Марта 2024"//
    public static String getPreviousMonthDate() {
        LocalDate previousMonthDate = LocalDate.now().minusMonths(1).plusDays(2);
        return previousMonthDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU")));
    }

    //Возвращает день недели и последний день месяца 2 месяца назад в формате "Choose четверг, 31 октября 2024 г."//
    public static String getLastDayTwoMonthsAgo() {
        LocalDate previousMonthDate = LocalDate.now().minusMonths(2);
        LocalDate lastDayOfMonth = previousMonthDate.withDayOfMonth(previousMonthDate.lengthOfMonth());
        String dayOfWeek = lastDayOfMonth.format(DateTimeFormatter.ofPattern("EEEE", new Locale("ru")));
        String formattedDate = lastDayOfMonth.format(DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", new Locale("ru")));
        return "Choose " + dayOfWeek + ", " + formattedDate;
    }

    //Возвращает день недели и первый день месяца 2 месяца назад в формате "Choose вторник, 1 октября 2024 г."//
    public static String getFirstDayTwoMonthsAgo() {
        LocalDate previousMonthDate = LocalDate.now().minusMonths(2);
        LocalDate firstDayOfMonth = previousMonthDate.withDayOfMonth(1);
        String dayOfWeek = firstDayOfMonth.format(DateTimeFormatter.ofPattern("EEEE", new Locale("ru")));
        String formattedDate = firstDayOfMonth.format(DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", new Locale("ru")));
        return "Choose " + dayOfWeek + ", " + formattedDate;
    }

    public static String getPreviousPeriod() {
        LocalDate previousMonthDate = LocalDate.now().minusMonths(2);
        LocalDate firstDayOfMonth = previousMonthDate.withDayOfMonth(1);
        LocalDate lastDayOfMonth = previousMonthDate.withDayOfMonth(previousMonthDate.lengthOfMonth());
        String lastDay = lastDayOfMonth.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", new Locale("ru")));
        String firstDay = firstDayOfMonth.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", new Locale("ru")));
        return firstDay + " - " + lastDay;
    }
}

