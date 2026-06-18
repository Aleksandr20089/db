package Detailig.db.dto;

public record ImageDto(
        Long id  ,
        String name ,
        String originalFileName ,
        Long size,
        String contentType
) {
}
