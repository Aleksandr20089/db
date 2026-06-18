package Detailig.db.repository;

import Detailig.db.entiti.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem , Long> {
}
