package Detailig.db.dto;

import jakarta.validation.constraints.Email;

public record EmailDto(
        String email ,
        String code) {


}
