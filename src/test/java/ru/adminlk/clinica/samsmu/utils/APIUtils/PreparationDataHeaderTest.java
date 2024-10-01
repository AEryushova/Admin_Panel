package ru.adminlk.clinica.samsmu.utils.APIUtils;

import com.google.gson.JsonObject;
import lombok.Getter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static ru.adminlk.clinica.samsmu.data.AppData.*;

public class PreparationDataHeaderTest {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Getter
    private static String tokenAdmin;

    public static void authAdmin(String login, String password) {
        tokenGetAuthAdmin(login, password);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(URL_BACK + "/api/admins/admin-data"))
                    .header("Authorization", "Bearer " + tokenAdmin)
                    .header("Environment", ENVIRONMENT)
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Admin has been successfully authorized");
            } else {
                System.out.println("Failed to log in as administrator. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void tokenGetAuthAdmin(String login, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("password", password);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                    .uri(URI.create(URL_BACK + "/api/admins/sign-in"))
                    .header("Environment", ENVIRONMENT)
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                System.out.println("Token successfully received");
                JsonObject jsonResponse = com.google.gson.JsonParser.parseString(response.body()).getAsJsonObject();
                tokenAdmin = jsonResponse.get("accessToken").getAsString();
            } else {
                System.out.println("Failed to get token. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void changePasswordAdmin(String login, String newPassword) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("newPassword", newPassword);
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))
                    .uri(URI.create(URL_BACK + "/api/admins/reset-password"))
                    .header("Authorization", "Bearer " + tokenAdmin)
                    .header("Environment", ENVIRONMENT)
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Password changed successfully");
            } else {
                System.out.println("Failed to change admin password. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


