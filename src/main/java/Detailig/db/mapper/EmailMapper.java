package Detailig.db.mapper;

import Detailig.db.dto.EmailDto;
import Detailig.db.entiti.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "sprig")
public interface EmailMapper {
    EmailDto toDto(User user);
}
