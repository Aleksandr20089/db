package Detailig.db.service;

import Detailig.db.dto.CreatWorkerDto;
import Detailig.db.entiti.Worker;
import Detailig.db.image.Image;
import Detailig.db.image.ImageRepository;
import Detailig.db.image.ImageService;
import Detailig.db.mapper.WorkMapper;
import Detailig.db.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final WorkMapper workMapper;
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    public void creatWorker (CreatWorkerDto creatWorkerDto){
        Worker worker = workMapper.toEntity(creatWorkerDto);
        workerRepository.save(worker);
    }
    public void updateExperienceWorker(Long id, Byte newExperience){
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Работник с таким айди не найден"));
        worker.setExperience(newExperience);
        workerRepository.save(worker);
    }

    public void addAvatar(Long id , MultipartFile file) throws IOException {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Работник с таким айди не найден"));

        if (worker.getImage() != null){
            imageRepository.delete(worker.getImage());
            worker.setImage(null);
        }

        Image image = imageService.toImageEntity(file);
        image.setWorker(worker);
        worker.setImage(image);
        workerRepository.save(worker);
    }

    public void deleteWorker(Long id){
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Работник с таким айди не найден"));

        if ((worker.getImage() != null)){
            imageRepository.delete(worker.getImage());{
            }
        }
        workerRepository.delete(worker);
    }
}
