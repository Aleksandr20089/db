package Detailig.db.repository;

import Detailig.db.entiti.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByCode(String code);
    Optional<User> findByEmail(String email);

}
