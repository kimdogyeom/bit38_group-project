package bit38_7.MapConvertor.domain.user;

import lombok.Data;


@Data
public class User {

	// 서버에 저장되는 유저id
	private Long userId;
	private String loginId;
	private String password;
	private String userName;
	private String email;

	public User() {
	}
}
