package ru.adminlk.clinica.samsmu.utils.APIUtils;


import ru.adminlk.clinica.samsmu.test.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import static ru.adminlk.clinica.samsmu.data.AppData.*;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PreparationDataSettingTest {

    @Getter
    public static String location;


    public static void uploadPhotoToStorage(File file) {
        Response response = given()
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file)
                .when()
                .post("/api/storage/upload")
                .then()
                .statusCode(201)
                .extract()
                .response();
        location = response.getBody().jsonPath().getString("location");
    }

    public static void deleteImageInStorage() {
                 given()
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .when()
                .delete(location)
                .then()
                .statusCode(204);

    }

    public static void uploadLogo(File file) {
        given()
                .baseUri(URL_BACK)
                .header("Authorization", "Bearer " + BaseTest.token)
                .header("Environment", ENVIRONMENT)
                .contentType(ContentType.MULTIPART)
                .multiPart("file", file)
                .when()
                .put("/api/storage/logo.png")
                .then()
                .statusCode(201);
    }

}

