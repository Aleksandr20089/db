package Detailig.db.mapper;

import Detailig.db.dto.MeDto;
import Detailig.db.dto.RegistrationDto;
import Detailig.db.entiti.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity (RegistrationDto registrationDto);
    MeDto toDto (User user);
}
