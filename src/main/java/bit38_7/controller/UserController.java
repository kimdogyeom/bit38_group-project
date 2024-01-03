package bit38_7.controller;


import bit38_7.domain.User;
import bit38_7.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("user-api")
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("join")
    public ResponseEntity<String> join(@RequestBody String userid, String password, String userName){
        User user = new User(userid, password, userName);
        Boolean result = userService.join(user);

        if (!result) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회원가입 실패");
        }
        return ResponseEntity.ok().body("회원가입 성공");
    }
}
