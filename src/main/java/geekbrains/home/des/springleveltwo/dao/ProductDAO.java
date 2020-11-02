package geekbrains.home.des.springleveltwo.dao;

import geekbrains.home.des.springleveltwo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Long> {
    Product findByTitle(String title);
}
