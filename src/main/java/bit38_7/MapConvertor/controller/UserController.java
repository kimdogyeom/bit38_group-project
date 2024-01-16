package bit38_7.MapConvertor.controller;


import bit38_7.MapConvertor.domain.user.User;
import bit38_7.MapConvertor.dto.InfoRequest;
import bit38_7.MapConvertor.interceptor.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

	@GetMapping("/users/info")
	public ResponseEntity<?> userInfo(HttpServletRequest request) {

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);

		InfoRequest responseUser = new InfoRequest();
		responseUser.setLoginId(user.getLoginId());
		responseUser.setUserName(user.getUserName());

		return ResponseEntity.ok().body(responseUser);
	}

}
