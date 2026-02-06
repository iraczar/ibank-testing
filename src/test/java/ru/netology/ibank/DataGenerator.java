package ru.netology.ibank;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("en"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private DataGenerator() {
    }

    // Метод для создания пользователя через API
    private static void sendRequest(UserRegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    // Генерация случайного логина
    public static String getRandomLogin() {
        return faker.name().username();
    }

    // Генерация случайного пароля
    public static String getRandomPassword() {
        return faker.internet().password();
    }

    // Метод для регистрации активного пользователя
    public static UserInfo getRegisteredUser(String status) {
        String login = getRandomLogin();
        String password = getRandomPassword();
        UserRegistrationDto user = new UserRegistrationDto(login, password, status);
        sendRequest(user);
        return new UserInfo(login, password, status);
    }

    // Класс для хранения информации о пользователе
    @Value
    public static class UserInfo {
        String login;
        String password;
        String status;
    }
}