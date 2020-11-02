package geekbrains.home.des.springleveltwo.service;

import geekbrains.home.des.springleveltwo.domain.User;
import geekbrains.home.des.springleveltwo.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getAll();
    User findByName(String name);
    boolean save(UserDTO userDTO);
    void save(User user);
    boolean update(User user);
    void update(UserDTO userDTO);
    User findById(Long id);
    void deleteById(Long id);
}
