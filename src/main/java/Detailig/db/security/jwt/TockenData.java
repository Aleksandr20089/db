package Detailig.db.security.jwt;

import Detailig.db.entiti.Role;

import java.util.Set;

public record TockenData(Long id, Set<Role> role) {
}
