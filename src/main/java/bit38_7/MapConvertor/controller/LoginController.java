package bit38_7.MapConvertor.controller;

import bit38_7.MapConvertor.domain.user.User;
import bit38_7.MapConvertor.dto.LoginRequest;
import bit38_7.MapConvertor.interceptor.session.SessionConst;
import bit38_7.MapConvertor.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(HttpServletRequest request, @RequestBody LoginRequest loginRequest) {
		User user = new User();
		user.setLoginId(loginRequest.getLoginId());
		user.setPassword(loginRequest.getPassword());

		log.info("user = {}",user);

		User loginResult = userService.login(user);

		if (loginResult == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body("아이디 또는 비밀번호가 맞지 않습니다.");
		}

		// 로그인 성공 처리
		// HttpSession을 사용해서 세션처리

		// 세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
		HttpSession session = request.getSession(true);
		// 세션에 로그인 회원 정보 보관
		session.setAttribute(SessionConst.LOGIN_MEMBER, loginResult);


		return ResponseEntity.ok().body("로그인 성공");
	}


	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		if (session != null) {
			session.invalidate();
			return ResponseEntity.ok().body("로그아웃 성공 세션삭제");

		}

		return ResponseEntity.ok().body("로그아웃 성공 세션 없었음");
	}
}
