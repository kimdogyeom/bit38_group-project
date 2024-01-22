package bit38_7.MapConvertor.controller;

import bit38_7.MapConvertor.domain.user.User;
import bit38_7.MapConvertor.dto.BuildingInfo;
import bit38_7.MapConvertor.interceptor.session.SessionConst;
import bit38_7.MapConvertor.service.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

//	@PostMapping("/file/save")
	public ResponseEntity<?> test(){
		return ResponseEntity.ok().body("저장 성공");
	}


	/**
	 * @RequestPart만 있을 때 ResourceHttpRequestHandler로 처리가 된다 왜?????
	 */
	@PostMapping("test/save")
	public ResponseEntity<?> fileSave(@RequestPart("buildingInfo") String object,
										@RequestPart("building") MultipartFile file,
										@RequestPart("floor") List<MultipartFile> floors,
										HttpServletRequest request) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		BuildingInfo buildingInfo = objectMapper.readValue(object, BuildingInfo.class);
		log.info("buildingInfo = {}", buildingInfo);

		HttpSession session = request.getSession(false);
		// 세션에 로그인 회원 정보 보관
//		User user = (User)session.getAttribute(SessionConst.LOGIN_MEMBER);
//		int userId = user.getUserId().intValue();
		int userId = 1;

		int buildingId = fileService.buildingSave(userId, buildingInfo, file.getBytes());
		log.info("buildingId = {}", buildingId);

		for (MultipartFile floor : floors) {
			fileService.floorSave(buildingId, floor.getBytes());
		}

		return ResponseEntity.ok().body("저장 성공");
	}
}
