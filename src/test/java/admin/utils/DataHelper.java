package admin.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {

    //Возвращает текущую дату в английской локали в формате "01 April 2024"//
    public static String getCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy 'г.'", new Locale("ru", "RU"));
        return currentDate.format(formatter);
    }

    //Возвращает название текущего месяца и год в русской локали в формате "Апрель 2024"//
    public static String getCurrentMonthYear() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("ru", "RU"));
        String formattedDate = currentDate.format(formatter);
        formattedDate = formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);
        return formattedDate;
    }

    //Возвращает название следующего месяца и год в русской локали в формате "Май 2024"//
    public static String getFutureMonthYear() {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        int nextMonth = currentMonth % 12 + 1;
        int nextYear = currentMonth == 12 ? currentYear + 1 : currentYear;
        LocalDate nextMonthDate = LocalDate.of(nextYear, nextMonth, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("ru", "RU"));
        String formattedNextMonthYear = nextMonthDate.format(formatter);
        formattedNextMonthYear = formattedNextMonthYear.substring(0, 1).toUpperCase() + formattedNextMonthYear.substring(1);
        return formattedNextMonthYear;
    }

    //Возвращает название предыдущего месяца и год в русской локали в формате "Март 2024"//
    public static String getPreviousMonthYear() {
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        int previousMonth = currentMonth == 1 ? 12 : currentMonth - 1;
        int previousYear = currentMonth == 1 ? currentYear - 1 : currentYear;
        LocalDate previousMonthDate = LocalDate.of(previousYear, previousMonth, 1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("ru", "RU"));
        String formattedPreviousMonthYear = previousMonthDate.format(formatter);
        formattedPreviousMonthYear = formattedPreviousMonthYear.substring(0, 1).toUpperCase() + formattedPreviousMonthYear.substring(1);
        return formattedPreviousMonthYear;
    }

    //Возвращает день т.е только число, который наступит через 2 дня от текущей даты (текущего дня)//
    public static String generateFutureDayCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(2);
        int futureDay = futureDate.getDayOfMonth();
        return String.valueOf(futureDay);
    }

    //Возвращает дату, которая наступит через 2 дня от текущей даты в русской локали в формате "03 Апреля 2024"//
    public static String generateFutureDateCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(2);
        return futureDate.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("ru", "RU")));
    }

    //Возвращает дату, которая наступит через 2 дня от текущей даты в русской локали в формате "2024-04-03"//
    public static String convertDateForDB(){
        String futureDateString = generateFutureDateCurrentMonth();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        LocalDate date = LocalDate.parse(futureDateString, inputFormatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(outputFormatter);
    }

    //Преобразовывает строку с датой и временем в строку с датой в формате "2024-04-03"//
    public static String trimDateStringToDay(String dateTimeString) {
        if (dateTimeString != null && !dateTimeString.isEmpty()) {
            LocalDate date = LocalDate.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
            return date.toString();
        }
        return "";
    }

    //Возвращает дату, которая наступит через 2 дня от текущей даты в цифровом формате "03.04.2023"//
    public static String generateActivationDateCurrentMonth() {
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

    public static String getStringCategory() {
        String[] options = {"Лаборатория", "Диагностикa"};
        Random rand = new Random();
        return options[rand.nextInt(options.length)];
    }
}

