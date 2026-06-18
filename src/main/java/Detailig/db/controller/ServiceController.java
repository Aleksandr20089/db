package Detailig.db.controller;

import Detailig.db.dto.ServiceDto;
import Detailig.db.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    @PostMapping("/addImage/{id}")
    public ResponseEntity<?> addImages(@PathVariable Long id ,
                                       @RequestPart("files")List<MultipartFile> files) throws IOException {
        serviceService.addImages(id, files);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
