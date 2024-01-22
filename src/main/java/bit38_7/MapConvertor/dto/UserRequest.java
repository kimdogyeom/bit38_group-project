package bit38_7.MapConvertor.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Valid
public class UserRequest {
	private String loginId;
	private String password;
	private String userName;
	private String email;

}
