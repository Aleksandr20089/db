package Detailig.db.dto;

import Detailig.db.entiti.Worker;

import java.math.BigDecimal;
import java.util.Set;

public record ServiceDto(String name, BigDecimal price, Set<Long> workerId) {
}
