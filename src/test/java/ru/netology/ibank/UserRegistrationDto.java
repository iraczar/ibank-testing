package ru.netology.ibank;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class UserRegistrationDto {
    private String login;
    private String password;
    private String status;
}