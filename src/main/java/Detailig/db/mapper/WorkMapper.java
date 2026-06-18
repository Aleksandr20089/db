package Detailig.db.mapper;

import Detailig.db.dto.CreatWorkerDto;
import Detailig.db.entiti.Worker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkMapper {
    Worker toEntity(CreatWorkerDto creatWorkerDto);
}
