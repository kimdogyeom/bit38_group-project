package bit38_7.MapConvertor.controller;


import bit38_7.MapConvertor.domain.User;
import bit38_7.MapConvertor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("user-api")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<String> join(@RequestBody User user){
        System.out.println("user = " + user);
        Boolean result = userService.join(user);

        if (!result) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("회원가입 실패");
        }
        return ResponseEntity.ok().body("회원가입 성공");
    }
}
