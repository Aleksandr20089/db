package Detailig.db.service;

import Detailig.db.dto.UserCredentialsDto;
import Detailig.db.entiti.User;
import Detailig.db.repository.UserRepository;
import Detailig.db.security.jwt.JwtAuthenticationDto;
import Detailig.db.security.jwt.JwtService;
import Detailig.db.security.jwt.TockenData;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SingService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private User findByCredentials(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = userRepository.findByEmail(userCredentialsDto.email())
                .orElseThrow(() -> new UsernameNotFoundException("Пользователя с такой почтой нет"));
        if (passwordEncoder.matches(userCredentialsDto.password(), user.getPassword())) {
            return user;
        }
        throw new RuntimeException("Почта или пароль неверны");
    }
    public JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User users = findByCredentials(userCredentialsDto);
        TockenData tokenData = new TockenData(
                users.getId(),
                users.getRoles());

        String refreshToken = users.getRefreshToken();
        if (users.isEnabled() && refreshToken != null && !refreshToken.isBlank() && jwtService.validateToken(refreshToken)) {
            return jwtService.refreshBaseTocken(tokenData, refreshToken);
        }

        if (users.isEnabled()) {
            JwtAuthenticationDto jwtAuthenticationDto = jwtService.generateAuthToken(tokenData);
            users.setRefreshToken(jwtAuthenticationDto.getRefreshToken());
            userRepository.save(users);
            return jwtAuthenticationDto;
        } else {
            throw new IllegalArgumentException("Пользователь не подтвердил почту!");
        }
    }
}
