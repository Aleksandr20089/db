package Detailig.db.repository;

import Detailig.db.entiti.Cart;
import Detailig.db.entiti.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart , Long> {
     Optional<Cart> findByUser(User user);
}
