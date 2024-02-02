package bit38_7.MapConvertor.service;

import bit38_7.MapConvertor.domain.user.User;
import bit38_7.MapConvertor.dto.UserRequest;
import bit38_7.MapConvertor.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 로그인
     */
    public User login(User user) {
        return userRepository.login(user.getLoginId(), user.getPassword())
                .orElse(null);
    }

    /**
     *회원 가입
     */
    public User join(User user) {
        User save = userRepository.join(user);
        return save;
    }

    /**
     *아이디 찾기
     */
    public String findId(UserRequest userRequest) {
        String userId = userRepository.findUserId(userRequest);
        return userId;
    }

    /**
     *비밀번호 찾기
     */
    public String findPw(UserRequest userRequest) {
        String userPw = userRepository.findUserPw(userRequest);
        return userPw;
    }


//    private void validateDuplicateUser(User user) {
//        userRepository.findUserId(user.getLoginId())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 존재하는 회원입니다.");
//                });
//    }

}
