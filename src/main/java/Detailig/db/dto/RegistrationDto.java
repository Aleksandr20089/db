package Detailig.db.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationDto(
        @NotBlank(message = "Email не должен быть пустым")
        @Email(message = "Некорректный формат email")
        @Size(min = 3, max = 50, message = "Email должен быть от 3 до 50 символов")
        String email,

        @NotBlank(message = "Имя не должно быть пустым")
        @Size(min = 3, max = 30, message = "Имя должно быть от 3 до 30 символов")
        String name,

        @NotBlank(message = "Пароль не должен быть пустым")
        @Size(min = 6, max = 20, message = "Пароль должен быть от 6 до 20 символов")
        String password
) {
}