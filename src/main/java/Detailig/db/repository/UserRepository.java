package Detailig.db.repository;

import Detailig.db.entiti.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
