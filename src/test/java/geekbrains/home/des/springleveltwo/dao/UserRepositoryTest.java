package geekbrains.home.des.springleveltwo.dao;

import geekbrains.home.des.springleveltwo.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@SqlGroup({@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:initUsers.sql")})
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDAO userDAO;

//    @Test
//    void chekFindByName(){
//        // have
//        User user = new User();
//        user.setName("Ahmed");
//        user.setPassword("ahmedpass");
//        user.setEmail("ahmed@ahaha.ru");
//
//        entityManager.persist(user);
//
//        //execute
//        User superManUser = userDAO.findFirstByName("Ahmed");
//
//        //check
//        Assertions.assertNotNull(superManUser);
//        Assertions.assertEquals(user.getName(), superManUser.getName());
//        Assertions.assertEquals(user.getPassword(), superManUser.getPassword());
//        Assertions.assertEquals(user.getEmail(), superManUser.getEmail());
//    }

//    @Test
//    void checkFindByNameAfterSql(){
//        //execute
//        User adminUser = userDAO.findFirstByName("admin");
//
//        //check
//        Assertions.assertNotNull(adminUser);
//        Assertions.assertEquals("Admin", adminUser.getName());
//        Assertions.assertEquals("admin", adminUser.getPassword());
//        Assertions.assertEquals("admin@mail.ru", adminUser.getEmail());
//    }
}
