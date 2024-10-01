package ru.adminlk.clinica.samsmu.utils.APIUtils;

import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.time.Duration;

import static ru.adminlk.clinica.samsmu.data.AppData.*;
import static ru.adminlk.clinica.samsmu.test.BaseTest.token;

public class PreparationDataAdminTest {

    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void createAdmin(String login, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login", login);
        jsonObject.addProperty("password", password);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/admins/sign-up"))
                .header("Authorization", "Bearer " + token)
                .header("Environment", ENVIRONMENT)
                .header("Content-Type", "application/json")
                .POST(BodyPublishers.ofString(jsonObject.toString()))
                .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 201) {
                System.out.println("Admin created successfully");
            } else {
                System.out.println("Failed to create admin. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteAdmin(String login) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BACK + "/api/admins?login=" + login))
                .header("Authorization", "Bearer " + token)
                .header("Environment", ENVIRONMENT)
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Admin deleted successfully");
            } else {
                System.out.println("Failed to delete admin. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
