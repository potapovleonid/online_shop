package geekbrains.home.des.springleveltwo.dao;

import geekbrains.home.des.springleveltwo.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Order, Long> {
}
