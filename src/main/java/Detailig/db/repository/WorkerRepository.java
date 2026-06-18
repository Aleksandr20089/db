package Detailig.db.repository;

import Detailig.db.entiti.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    List<Worker> id(Long id);
}
