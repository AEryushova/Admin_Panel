package ru.adminlk.clinica.samsmu.utils.testsUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;


public class TestHelper {

    //Генерирует и возвращает UUID для SQL-запросов//
    public static UUID generateUuid() {
        return UUID.randomUUID();
    }


    //Генерирует дату в зависимости от периода в параметре и возвращает дату и время в формате Timestamp для SQL-запросов//
    public static Timestamp generateDate(String period) {
        int monthOffset = 2;
        switch (period) {
            case "previous":
                return Timestamp.valueOf(LocalDate.now().minusMonths(monthOffset).atStartOfDay());
            case "future":
                return Timestamp.valueOf(LocalDate.now().plusMonths(monthOffset).atStartOfDay());
            case "current":
                return Timestamp.valueOf(LocalDateTime.now());
            case "yesterday":
                return Timestamp.valueOf(LocalDateTime.now().minusDays(1));
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
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


    //Возвращает дату в зависимости от входящих параметров (период, кол-во месяцев, кол-во дней, формат даты)//
    public static String getDate(String period, int monthOffset, int dayOffset, String datePattern) {
        LocalDate date;
        switch (period) {
            case "current":
                date = LocalDate.now().plusDays(dayOffset);
                break;
            case "previous":
                date = LocalDate.now().minusMonths(monthOffset).plusDays(dayOffset);
                break;
            case "future":
                date = LocalDate.now().plusMonths(monthOffset).plusDays(dayOffset);
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
        return date.format(DateTimeFormatter.ofPattern(datePattern, new Locale("ru", "RU"))).toLowerCase();
    }


    //Возвращает день недели и день месяца в зависимости от входящих параметров (период, тип дня, кол-во месяцев, кол-во дней) в формате "Choose четверг, 31 октября 2024 г."//
    public static String getDay(String period, String dayType, int monthOffset, int dayOffset) {
        LocalDate date;
        switch (period) {
            case "current":
                date = LocalDate.now();
                break;
            case "previous":
                date = LocalDate.now().minusMonths(monthOffset);
                break;
            case "future":
                date = LocalDate.now().plusMonths(monthOffset);
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + period);
        }
        if (dayType != null) {
            if (dayType.equals("first")) {
                date = date.withDayOfMonth(1);
            } else if (dayType.equals("last")) {
                date = date.withDayOfMonth(date.lengthOfMonth());
            } else {
                throw new IllegalArgumentException("Invalid day type: " + dayType);
            }
        } else {
            date = date.plusDays(dayOffset);
        }
        String dayOfWeek = date.format(DateTimeFormatter.ofPattern("EEEE", new Locale("ru")));
        String formattedDate = date.format(DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", new Locale("ru")));
        return "Choose " + dayOfWeek + ", " + formattedDate;
    }


    //Возвращает период в зависимости от входящих параметров (период, кол-во месяцев, последние 30 дней) в формате 01.09.2024-30.09.2024"//
    public static String getPeriod(String period, int monthOffset, boolean lastThirtyDays) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", new Locale("ru"));
        LocalDate startDate;
        LocalDate endDate;
        if (lastThirtyDays) {
            startDate = LocalDate.now().minusDays(30);
            endDate = LocalDate.now();
        } else {
            LocalDate month;
            switch (period) {
                case "previous":
                    month = LocalDate.now().minusMonths(monthOffset);
                    break;
                case "future":
                    month = LocalDate.now().plusMonths(monthOffset);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid period: " + period);
            }
            startDate = month.withDayOfMonth(1);
            endDate = month.withDayOfMonth(month.lengthOfMonth());
        }
        String firstDay = startDate.format(formatter);
        String lastDay = endDate.format(formatter);
        return firstDay + " - " + lastDay;
    }


    //Возвращает текущую дату (+2 дня) формате "03.04.2023"//
    @SuppressWarnings("unused")
    public static String chooseDayCurrentMonth() {
        int dayOffset = 2;
        return LocalDate.now().plusDays(dayOffset).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}

