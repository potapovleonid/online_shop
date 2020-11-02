package geekbrains.home.des.springleveltwo.service;

import geekbrains.home.des.springleveltwo.dao.UserDAO;
import geekbrains.home.des.springleveltwo.domain.User;
import geekbrains.home.des.springleveltwo.dto.UserDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public class UserServiceImplTest {
    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    private UserDAO userDAO;

    @BeforeAll
    static void beforeAll(){
        System.out.println("Before all test");
    }

    @BeforeEach
    void setUp(){
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userDAO = Mockito.mock(UserDAO.class);

        userService = new UserServiceImpl(userDAO, passwordEncoder);
    }

    @AfterEach
    void afterEach(){
        System.out.println("After each test");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("After all test");
    }

    @Test
    void checkFindByName(){
        //have
        String name = "petr";
        User testuser = User.builder().id(1L).name(name).build();

        Mockito.when(userDAO.findFirstByName(Mockito.anyString())).thenReturn(testuser);

        //execute
        User newUser = userService.findByName(name);

        Assertions.assertNotNull(newUser);
        Assertions.assertEquals(newUser, testuser);
    }

    @Test
    void checkFindByNameExact(){
        //have
        String name = "testuser";
        User expectedUser = User.builder().id(1L).name(name).build();

        Mockito.when(userDAO.findFirstByName(Mockito.eq(name))).thenReturn(expectedUser);

        //execute
        User actualUser = userService.findByName(name);
        User rndUser = userService.findByName(UUID.randomUUID().toString());

        //check
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);

        Assertions.assertNull(rndUser);
    }

    @Test
    void checkSaveIncorrectPassword(){
        //have
        UserDTO userDTO = UserDTO.builder()
                .password("password")
                .matchPassword("test")
                .build();

        //execute
        Assertions.assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                userService.save(userDTO);
            }
        });
    }

    @Test
    void checkSave(){
        //have
        UserDTO userDTO = UserDTO.builder()
                .username("ahmed")
                .email("ahmed@ahaha.ru")
                .password("ahmedpass")
                .matchPassword("ahmedpass")
                .build();

        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("ahmedpass");

        //execute
        boolean res = userService.save(userDTO);

        //check
        Assertions.assertTrue(res);
        Mockito.verify(passwordEncoder).encode(Mockito.anyString());
        Mockito.verify(userDAO).save(Mockito.any());
    }
}
