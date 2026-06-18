package Detailig.db.mapper;

import Detailig.db.dto.ServiceDto;
import Detailig.db.entiti.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    @Mapping(target = "workers", ignore = true)
    @Mapping(target = "id" , ignore = true)
    Service toEntity(ServiceDto serviceDto);


}
