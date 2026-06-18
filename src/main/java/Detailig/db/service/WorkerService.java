package Detailig.db.service;

import Detailig.db.dto.CreatWorkerDto;
import Detailig.db.entiti.Worker;
import Detailig.db.mapper.WorkMapper;
import Detailig.db.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkerService {
    private final WorkerRepository workerRepository;
    private final WorkMapper workMapper;

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
}
