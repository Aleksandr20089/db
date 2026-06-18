package Detailig.db.controller;

import Detailig.db.dto.RegistrationDto;

import Detailig.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody RegistrationDto registrationDto) {
        try {
           userService.createUser(registrationDto);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/confirm-registration")
    public ResponseEntity<?> registerUser(@RequestParam String code) {
        try {
            userService.confirmRegistration(code);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
