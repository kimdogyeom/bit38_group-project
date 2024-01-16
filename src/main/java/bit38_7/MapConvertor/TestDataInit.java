package bit38_7.MapConvertor;

import bit38_7.MapConvertor.domain.user.User;
import bit38_7.MapConvertor.repository.MemoryUserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemoryUserRepository memoryUserRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        User user = new User();
        user.setId(1L);
        user.setLoginId("test");
        user.setPassword("test!");
        user.setUserName("테스터");
        user.setEmail("test@test.com");
        memoryUserRepository.save(user);
    }

}