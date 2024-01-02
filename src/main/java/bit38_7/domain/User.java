package bit38_7.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class User {


	// 서버에 저장되는 유저id
	private Long id;

	private String loginId;
	private String password;
	private String userName;
}
