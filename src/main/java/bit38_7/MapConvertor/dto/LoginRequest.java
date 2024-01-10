package bit38_7.MapConvertor.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginRequest {

	private String loginId;
	private String password;
}
