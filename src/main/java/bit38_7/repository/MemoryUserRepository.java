package bit38_7.repository;

import bit38_7.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryUserRepository implements UserRepository {

    private static Map<Long,User> userList = new HashMap<>();


    @Override
    public User save(User user) {
        userList.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findUserLoginId(String name) {
        return userList.values().stream().
                filter(user -> user.getLoginId().equals(name))
                .findAny();
    }


}
