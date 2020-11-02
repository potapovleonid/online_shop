package geekbrains.home.des.springleveltwo.service;

import geekbrains.home.des.springleveltwo.dao.UserDAO;
import geekbrains.home.des.springleveltwo.domain.User;
import geekbrains.home.des.springleveltwo.domain.UserRole;
import geekbrains.home.des.springleveltwo.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
//        initializeDB();
    }

    private void initializeDB() {
        userDAO.saveAll(Arrays.asList(
           new User(null, "Admin", passwordEncoder.encode("admin"), "admin@mail.ru", false, UserRole.ADMIN, null),
           new User(null, "Leonid", passwordEncoder.encode("leonid"), "leonid@gmail.com", false, UserRole.SUPER_ADMIN, null),
           new User(null, "Test", passwordEncoder.encode("test"), "test@gmail.com", false, UserRole.USER, null)
        ));
    }

    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }

    @Override
    public boolean save(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getPassword(), userDTO.getMatchPassword())){
            throw new RuntimeException("password is not identical");
        }
        User user = User.builder()
                .name(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .role(UserRole.USER)
                .build();
        userDAO.save(user);
        return true;
    }

    @Override
    public void save(User user) {
        User findUser = userDAO.findFirstByName(user.getName());
        //Кодируем в bcrypt пароль
        if (findUser == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDAO.save(user);
        }
    }

    @Override
    public void update(UserDTO userDTO) {
        User updateUser = userDAO.findFirstByName(userDTO.getUsername());
        if (updateUser == null){
            throw new RuntimeException("User not found by name: " + userDTO.getUsername());
        }

        boolean changed = false;
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()){
            updateUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            changed = true;
        }
        if (!Objects.equals(userDTO.getEmail(), updateUser.getEmail())){
            updateUser.setEmail(userDTO.getEmail());
            changed = true;
        }
        if (changed){
            userDAO.save(updateUser);
        }
    }

    @Override
    public User findByName(String name) {
        return userDAO.findFirstByName(name);
    }

    @Override
    public boolean update(User user) {
        if (user == null){
            throw new RuntimeException("Enter null user");
        }
        userDAO.save(user);
        return true;
    }

    @Override
    public User findById(Long id) {
        return userDAO.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User findUser = userDAO.findFirstByName(s);
        if (findUser == null){
            throw  new UsernameNotFoundException("User not found with name: " + s);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(findUser.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                findUser.getName(),
                findUser.getPassword(),
                roles);
    }

    @Override
    public void deleteById(Long id) {
        userDAO.deleteById(id);
    }
}
