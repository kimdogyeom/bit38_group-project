package bit38_7.MapConvertor.service;

import bit38_7.MapConvertor.domain.user.User;
import bit38_7.MapConvertor.repository.MemoryUserRepository;
import bit38_7.MapConvertor.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository = new MemoryUserRepository();

    public User login(User user) {
        return userRepository.findByLoginId(user.getLoginId())
            .filter(m -> m.getPassword().equals(user.getPassword()))
            .orElse(null);
    }


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
        userRepository.findUserName(user.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

}
