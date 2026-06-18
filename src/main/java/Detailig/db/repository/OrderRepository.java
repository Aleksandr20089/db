package Detailig.db.repository;

import Detailig.db.entiti.Order;
import Detailig.db.entiti.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository  extends JpaRepository<Order , Long> {
    Optional<Order> findByUser(User user);
}
