package bit38_7.MapConvertor.interceptor;

import bit38_7.MapConvertor.domain.user.User;
import bit38_7.MapConvertor.interceptor.session.SessionConst;
import bit38_7.MapConvertor.repository.file.JdbcFileRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

@Slf4j
public class BuildingCheckInterceptor implements HandlerInterceptor {

    private final JdbcFileRepository jdbcFileRepository;

    public BuildingCheckInterceptor(JdbcFileRepository jdbcFileRepository) {
        this.jdbcFileRepository = jdbcFileRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HttpSession session = request.getSession(false);
        User user = (User)session.getAttribute(SessionConst.LOGIN_MEMBER);
        int userId = user.getUserId().intValue();


        final Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        log.info("pathVariables={}", pathVariables);
        final int buildingId = Integer.parseInt(pathVariables.get("buildingId"));
        log.info("buildingId={}", buildingId);

        int results = jdbcFileRepository.findById(userId,buildingId);

        if (results == 0) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        return true;
    }

}
