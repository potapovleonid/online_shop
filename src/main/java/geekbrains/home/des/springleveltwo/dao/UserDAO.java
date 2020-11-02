package geekbrains.home.des.springleveltwo.dao;

import geekbrains.home.des.springleveltwo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
    User findFirstByName(String name);
    void deleteUserById(Long id);
}
