package bit38_7.service;

import bit38_7.domain.User;
import bit38_7.repository.MemoryUserRepository;
import bit38_7.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {

    private final UserRepository userRepository = new MemoryUserRepository();

    public Boolean join(User user) {
        try {
            validateDuplicateUser(user);
            userRepository.save(user);
            return true;
        } catch (IllegalStateException e) {
            log.info("validateDuplicateUser = {}", e.getMessage());
            return false;
        }
    }

    private void validateDuplicateUser(User user) {
        userRepository.findUserLoginId(user.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

}
