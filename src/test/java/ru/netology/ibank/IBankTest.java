package ru.netology.ibank;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class IBankTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulLoginIfActiveUser() {
        // Создаем активного пользователя
        var registeredUser = DataGenerator.getRegisteredUser("active");

        // Вводим логин
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());

        // Вводим пароль
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());

        // Нажимаем кнопку "Продолжить"
        $("button.button").click();

        // Проверяем успешный вход
        $("h2").shouldHave(Condition.exactText("Личный кабинет"));
    }

    @Test
    void shouldGetErrorIfBlockedUser() {
        // Создаем заблокированного пользователя
        var blockedUser = DataGenerator.getRegisteredUser("blocked");

        // Вводим логин
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());

        // Вводим пароль
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());

        // Нажимаем кнопку "Продолжить"
        $("button.button").click();

        // Проверяем сообщение об ошибке
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldGetErrorIfWrongLogin() {
        // Создаем пользователя
        var registeredUser = DataGenerator.getRegisteredUser("active");

        // Вводим НЕПРАВИЛЬНЫЙ логин
        $("[data-test-id='login'] input").setValue(DataGenerator.getRandomLogin());

        // Вводим правильный пароль
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());

        // Нажимаем кнопку "Продолжить"
        $("button.button").click();

        // Проверяем сообщение об ошибке
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldGetErrorIfWrongPassword() {
        // Создаем пользователя
        var registeredUser = DataGenerator.getRegisteredUser("active");

        // Вводим правильный логин
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());

        // Вводим НЕПРАВИЛЬНЫЙ пароль
        $("[data-test-id='password'] input").setValue(DataGenerator.getRandomPassword());

        // Нажимаем кнопку "Продолжить"
        $("button.button").click();

        // Проверяем сообщение об ошибке
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
    }
}