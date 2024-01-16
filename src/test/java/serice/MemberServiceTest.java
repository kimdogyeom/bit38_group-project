package serice;

import bit38_7.MapConvertor.domain.user.User;
import bit38_7.MapConvertor.service.UserService;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    UserService userList = new UserService();

    @Test
    public void join() {
        User user = new User("kim", "12344", "dodo", "test@test.com");
        userList.join(user);
        User user1 = new User("kim", "12344", "dodo","test@test.com");
        userList.join(user1);

    }
}
