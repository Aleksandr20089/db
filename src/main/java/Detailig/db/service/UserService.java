package Detailig.db.service;

import Detailig.db.dto.EmailDto;
import Detailig.db.dto.MeDto;
import Detailig.db.dto.RegistrationDto;
import Detailig.db.email.EmailService;
import Detailig.db.entiti.Role;
import Detailig.db.entiti.User;
import Detailig.db.mapper.EmailMapper;
import Detailig.db.mapper.UserMapper;
import Detailig.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final EmailMapper emailMapper;

    public void createUser(RegistrationDto registrationDto) {
        User user = userMapper.toEntity(registrationDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        user.setCode(code);
        user.setRoles(Set.of(Role.USER));
        EmailDto emailDto = emailMapper.toDto(user);
        emailService.sendEmail(emailDto);
        userRepository.save(user);
    }
    public void confirmRegistration(String code) {
        User user = userRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден "));
        user.setCode(null);
        user.setEnabled(true);
        userRepository.save(user);
    }
    @Transactional(readOnly = true)
    public MeDto meDto (Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Пользователь с таким айди не найден "));
        return userMapper.toDto(user);
    }


}
