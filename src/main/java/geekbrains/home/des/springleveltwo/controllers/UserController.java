package geekbrains.home.des.springleveltwo.controllers;

import geekbrains.home.des.springleveltwo.domain.User;
import geekbrains.home.des.springleveltwo.dto.UserDTO;
import geekbrains.home.des.springleveltwo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String allUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PreAuthorize("isAuthenticated() and hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user_new";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String getUser(Model model, Principal principal){
        if (principal == null){
            throw new RuntimeException("You are not autorize");
        }
        User user = userService.findByName(principal.getName());

        UserDTO dto = UserDTO.builder()
                .username(user.getName())
                .email(user.getEmail())
                .build();
        model.addAttribute("userdto", dto);
        return "profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/profile")
    public String updateProfile(Model model, Principal principal, UserDTO dto){
        if (principal == null ||
        !Objects.equals(principal.getName(), dto.getUsername())) {
            throw new RuntimeException("You are not autorize");
        }
        if (dto.getPassword() != null
        && !dto.getPassword().isEmpty()
        && !Objects.equals(dto.getPassword(), dto.getMatchPassword())){
            model.addAttribute("user", dto);
            return "/users/profile";
        }
        userService.update(dto);
        return "redirect:/users/profile";
    }

    @PreAuthorize("isAuthenticated() and hasAnyAuthority('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/new")
    public String newUserAdd(Model model, UserDTO userDTO) {
        if (userService.save(userDTO)){
            return "redirect:/users";
        } else {
            return "redirect:/users/new";
        }
    }

    @PreAuthorize("isAuthenticated() and hasAuthority('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public String getUser(Model model, @PathVariable Long id) {
        model.addAttribute("user", userService.findById(id));
        return "user";
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/{id}")
    public String updateUser(User userForm, @PathVariable Long id) {
        if (userService.update(userForm)) {
            return "redirect:/users";
        } else {
            return "redirect:/users/" + id;
        }
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/{id}/delete")
    public void deleteUser(@PathVariable Long id){
        userService.deleteById(id);
    }

}
