package Detailig.db.service;

import Detailig.db.dto.ServiceDto;
import Detailig.db.entiti.Worker;
import Detailig.db.image.Image;
import Detailig.db.image.ImageService;
import Detailig.db.mapper.ServiceMapper;
import Detailig.db.repository.ServiceRepository;
import Detailig.db.repository.UserRepository;
import Detailig.db.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServiceMapper serviceMapper;
    private final WorkerRepository workerRepository;
    private final ImageService imageService;

    public void creatService(ServiceDto serviceDto) {
        Detailig.db.entiti.Service service = serviceMapper.toEntity(serviceDto);
        Set<Worker> workers = new HashSet<>();
        for (Long id : serviceDto.workerId()) {
            Worker worker = workerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Работник с таким айди не найден"));
            workers.add(worker);
        }
        service.setWorkers(workers);
        serviceRepository.save(service);
    }

    public void addImages(Long id, List<MultipartFile> files) throws IOException {
        Detailig.db.entiti.Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Улсуга не найденна"));
        List<Image> images = service.getImages();

        for (MultipartFile file : files){
            Image image = imageService.toImageEntity(file);
            image.setService(service);
            images.add(image);
        }
        serviceRepository.save(service);
    }

}
