package bit38_7.MapConvertor.interceptor;

import bit38_7.MapConvertor.domain.user.User;
import bit38_7.MapConvertor.interceptor.session.SessionConst;
import bit38_7.MapConvertor.repository.file.JdbcFileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class BuildingCheckInterceptor implements HandlerInterceptor {

    private final JdbcFileRepository jdbcFileRepository;

    @Autowired
    public BuildingCheckInterceptor(JdbcFileRepository jdbcFileRepository) {
        this.jdbcFileRepository = jdbcFileRepository;
    }

    //
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute(SessionConst.LOGIN_MEMBER);
        int userId = user.getUserId().intValue();

        String requestURI = request.getRequestURI(); //요청 url 온것을 받는 것
        String[] result = requestURI.split("/", 4);
        int BuildingId = Integer.parseInt(result[2]);

        int results = jdbcFileRepository.FindById(userId,BuildingId);

        if (results == 0) {
            log.info("잘못 입력된 빌딩 정보와 유저 정보 입니다");
            return false;
        }
        return true;
    }

}
