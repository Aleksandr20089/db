package Detailig.db.controller;

import Detailig.db.dto.UserCredentialsDto;
import Detailig.db.security.jwt.JwtAuthenticationDto;
import Detailig.db.service.SingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RequiredArgsConstructor
@RestController
@RequestMapping("api/singIn")
public class SingController {
    private final SingService singService;

    @PostMapping("/auth")
    public ResponseEntity<JwtAuthenticationDto> singIn(@RequestBody UserCredentialsDto userCredentialsDto){
        JwtAuthenticationDto jwtAuthenticationDto =  singService.singIn(userCredentialsDto);
        return ResponseEntity.ok(jwtAuthenticationDto);
    }
}
