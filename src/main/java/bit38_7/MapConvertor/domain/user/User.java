package bit38_7.MapConvertor.domain.user;

import lombok.Data;


@Data
public class User {

	// 서버에 저장되는 유저id
	private Long id;
	static private Long sequence = 0L;

	private String loginId;
	private String password;
	private String userName;
	private String email;

	public User() {
	}

	public User(String loginId, String password, String userName, String email) {
		this.id = ++sequence;
		this.loginId = loginId;
		this.password = password;
		this.userName = userName;
		this.email = email;
	}
}
