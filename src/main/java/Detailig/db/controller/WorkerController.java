package Detailig.db.controller;

import Detailig.db.dto.CreatWorkerDto;
import Detailig.db.service.WorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    @PostMapping
    public ResponseEntity<Void> createWorker(@RequestBody @Valid CreatWorkerDto dto) {
        workerService.creatWorker(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/experience")
    public ResponseEntity<Void> updateExperience(@PathVariable Long id,
                                                 @RequestParam Byte newExperience) {
        workerService.updateExperienceWorker(id, newExperience);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addImages/{id}")
    public ResponseEntity<?> addAvatar(@PathVariable Long id ,
                                       @RequestPart("file") MultipartFile file) throws IOException {
        workerService.addAvatar(id, file);
        return ResponseEntity.ok().build();
    }
}