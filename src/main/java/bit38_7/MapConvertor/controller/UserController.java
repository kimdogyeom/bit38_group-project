package bit38_7.MapConvertor.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("user-api")
public class UserController {


	@PostMapping("/transfer")
	public ResponseEntity transfer() {
		return ResponseEntity.ok().body("success");
	}
}
