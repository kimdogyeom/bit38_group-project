package bit38_7.testUser;

import static org.assertj.core.api.Assertions.*;

import bit38_7.MapConvertor.MapConvertorApplication;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest(classes = MapConvertorApplication.class)
@AutoConfigureMockMvc
public class TestUser {

	@Autowired
	private MockMvc mockMvc;


	@Test
	@WithMockUser(username = "user", password = "1234", roles = "USER")
	public void testUserAccess() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user-api/transfer"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		// 결과(result)를 통해 추가적인 테스트 수행 가능
		assertThat(result.getResponse().getContentAsString()).isEqualTo("success");
	}

}
