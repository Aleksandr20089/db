package Detailig.db.email;

import Detailig.db.dto.EmailDto;
import Detailig.db.dto.RegistrationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(EmailDto emailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.email());
        message.setSubject("Подтверждение регистраций: ");
        message.setText("Код регистрации: " + emailDto.code());
        mailSender.send(message);
    }

}
