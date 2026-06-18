package Detailig.db.mapper;

import Detailig.db.dto.ImageDto;
import Detailig.db.image.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto toImageDto(Image image);
}
