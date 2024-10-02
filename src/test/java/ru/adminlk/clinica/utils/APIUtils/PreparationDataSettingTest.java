package ru.adminlk.clinica.utils.APIUtils;

import com.google.gson.JsonParser;
import lombok.Getter;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


import okhttp3.*;
import static ru.adminlk.clinica.data.AppData.*;
import static ru.adminlk.clinica.test.BaseTest.token;

public class PreparationDataSettingTest {

    @Getter
    public static String location;

    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static void uploadPhotoToStorage(File file) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("image/jpeg")))
                .build();
        Request request = new Request.Builder()
                .url(URL_BACK + "/api/storage/upload")
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Environment", ENVIRONMENT)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                location = jsonResponse.get("location").getAsString();
                System.out.println("File uploaded successfully");
            } else {
                System.out.println("Failed to upload file. Status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void deleteImageInStorage() {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BACK+location))
                    .header("Authorization", "Bearer " + token)
                    .header("Environment", ENVIRONMENT)
                    .DELETE()
                    .build();
            try {
                HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("Image deleted successfully");
            } else {
                System.out.println("Failed to delete image. Status code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void uploadLogo(File file) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(file, MediaType.parse("image/png")))
                .build();
        Request request = new Request.Builder()
                .url(URL_BACK + "/api/storage/logo.png")
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("Environment", ENVIRONMENT)
                .put(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Logo uploaded successfully");
            } else {
                System.out.println("Failed to upload logo. Status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


