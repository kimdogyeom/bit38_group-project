package serice;

import bit38_7.domain.User;
import bit38_7.repository.MemoryUserRepository;
import bit38_7.repository.UserRepository;
import bit38_7.service.UserService;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    UserService userList = new UserService();

    @Test
    public void join() {
        User user = new User("kim", "12344", "dodo");
        userList.join(user);
        User user1 = new User("kim", "12344", "dodo");
        userList.join(user1);

    }
}
