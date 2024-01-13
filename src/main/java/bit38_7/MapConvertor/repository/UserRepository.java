package bit38_7.MapConvertor.repository;

import bit38_7.MapConvertor.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findUserName(String name);

	Optional<User> findByLoginId(String loginId);
}
