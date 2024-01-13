package bit38_7.MapConvertor.repository;

import bit38_7.MapConvertor.domain.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryUserRepository implements UserRepository {

    private static Map<Long,User> userList = new HashMap<>();


    @Override
    public User save(User user) {
        userList.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findUserName(String name) {
        return userList.values().stream().
                filter(user -> user.getLoginId().equals(name))
                .findAny();
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return userList.values().stream()
            .filter(user -> user.getLoginId().equals(loginId))
            .findFirst();
    }

    public Map<Long,User> getUserList(){
        return userList;
    }


}
