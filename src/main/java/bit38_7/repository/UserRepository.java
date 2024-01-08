package bit38_7.repository;

import bit38_7.domain.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findUserLoginId(String name);
}
