package bit38_7.MapConvertor.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRequest {
	String loginId;
	String userName;
	String email;
}
