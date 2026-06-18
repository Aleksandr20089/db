package Detailig.db.controller;

import Detailig.db.dto.ServiceDto;
import Detailig.db.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    public ResponseEntity<Void> createService(@RequestBody @Valid ServiceDto serviceDto) {
        serviceService.creatService(serviceDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
