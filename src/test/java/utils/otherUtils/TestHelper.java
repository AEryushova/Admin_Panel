package utils.otherUtils;

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
        LocalDateTime now = LocalDateTime.now();
        return Timestamp.valueOf(now);
    }

    //Генерирует и возвращает дату и время - 1 день от текущей даты в формате Timestamp для SQL-запросов//
    public static Timestamp getPreviousDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDaysAgo = now.minusDays(1);
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
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return currentDate.format(formatter);
    }


    //Возвращает текущую дату в русской локали в формате "01 Апреля 2024"//
    public static String getCurrentDateRu() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU"));
        return currentDate.format(formatter);
    }

    //Возвращает текущую дату в русской локали в формате "01 Апреля 2024 + г."//
    public static String getCurrentDateRuYear() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", new Locale("ru", "RU"));
        return currentDate.format(formatter);
    }

    //Возвращает название текущего месяца и год в русской локали в формате "апрель 2024"
    public static String getCurrentMonthYear() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("ru", "RU"));
        String formattedDate = currentDate.format(formatter);
        return formattedDate.toLowerCase();
    }

    //Возвращает название следующего месяца и год в русской локали в формате "май 2024"
    public static String getFutureMonthYear() {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        int nextMonth = currentMonth % 12 + 1;
        int nextYear = currentMonth == 12 ? currentYear + 1 : currentYear;
        LocalDate nextMonthDate = LocalDate.of(nextYear, nextMonth, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("ru", "RU"));
        return nextMonthDate.format(formatter).toLowerCase();
    }

    //Возвращает название предыдущего месяца и год в русской локали в формате "март 2024"//
    public static String getPreviousMonthYear() {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        int previousMonth = currentMonth == 1 ? 12 : currentMonth - 1;
        int previousYear = currentMonth == 1 ? currentYear - 1 : currentYear;
        LocalDate previousMonthDate = LocalDate.of(previousYear, previousMonth, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("ru", "RU"));
        return previousMonthDate.format(formatter).toLowerCase();
    }

    //Возвращает день т.е только число, который наступит через 2 дня от текущей даты (текущего дня)//
    public static String getFutureDayCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(2);
        int futureDay = futureDate.getDayOfMonth();
        return String.valueOf(futureDay);
    }

    //Возвращает дату, которая наступит через 2 дня от текущей даты в русской локали в формате "03 Апреля 2024"//
    public static String getFutureDateCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(2);
        return futureDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU")));
    }

    //Возвращает дату, которая наступит через 2 дня от текущей даты в цифровом формате "03.04.2023"//
    public static String getActivationDateCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return futureDate.format(formatter);
    }

    //Возвращает дату, которая наступит в следующем месяце + 2 дня от текущей даты в русской локали в формате "03 Май 2024"//
    public static String getNextMonthDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(2);
        int futureMonth = futureDate.getMonthValue();
        int futureYear = futureDate.getYear();

        if (futureMonth == 12) {
            futureMonth = 1;
            futureYear++;
        } else {
            futureMonth++;
        }
        LocalDate nextMonthDate = LocalDate.of(futureYear, futureMonth, futureDate.getDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU"));
        return nextMonthDate.format(formatter);
    }

    //Возвращает дату, вычесленную от текущей + 2 дня прошлого месяца в русской локали в формате "03 Марта 2024"//
    public static String getPreviousMonthDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(2);
        int prevMonth = futureDate.getMonthValue() - 1;
        int prevYear = futureDate.getYear();

        if (prevMonth == 0) {
            prevMonth = 12;
            prevYear--;
        }
        LocalDate prevMonthDate = LocalDate.of(prevYear, prevMonth, futureDate.getDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU"));
        return prevMonthDate.format(formatter);
    }
}

