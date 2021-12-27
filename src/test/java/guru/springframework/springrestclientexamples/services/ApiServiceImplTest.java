package guru.springframework.springrestclientexamples.services;

import static org.junit.Assert.assertEquals;

import guru.springframework.api.domain.User;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceImplTest {
  @Autowired ApiService apiService;

  @Before
  public void setUp() throws Exception {}

  @Test
  public void testGetUsers() {
    List<User> userList = apiService.getUsers(2);
    assertEquals(userList.size(), 2);
  }
  @Test
  public void testGetUser() {
    User user= apiService.getUser(1);
    assertEquals(user.getId(),(Integer) 1);
  }

}
