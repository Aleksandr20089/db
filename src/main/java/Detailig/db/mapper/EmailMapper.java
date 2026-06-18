package Detailig.db.mapper;

import Detailig.db.dto.EmailDto;
import Detailig.db.entiti.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    EmailDto toDto(User user);
}
