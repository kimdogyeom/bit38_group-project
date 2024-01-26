package bit38_7.MapConvertor.controller;

import bit38_7.MapConvertor.domain.user.User;
import bit38_7.MapConvertor.dto.Building;
import bit38_7.MapConvertor.dto.BuildingInfo;
import bit38_7.MapConvertor.dto.BuildingResponse;
import bit38_7.MapConvertor.dto.FloorInfo;
import bit38_7.MapConvertor.interceptor.session.SessionConst;
import bit38_7.MapConvertor.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;

	// "file" post로 받게 만들기
	// REST API 따르게 만들기
	@PostMapping(value = "file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> fileSave(@RequestParam("buildingName") String buildingName,
									@RequestParam("floorCount") int floorCount,
									@RequestPart("files") List<MultipartFile> floors) throws IOException {
		BuildingInfo buildingInfo = new BuildingInfo();
		buildingInfo.setBuildingName(buildingName);
		buildingInfo.setBuildingCount(floorCount);

		String url = "http://10.101.69.52:7070/model";

		List<byte[]> floorData = new ArrayList<>();

		for (MultipartFile floor : floors) {
			floorData.add(floor.getBytes());
		}
		MultiValueMap<String, List<byte[]>> params = new LinkedMultiValueMap<>();
		params.add("files", floorData);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<MultiValueMap<String, List<byte[]>>> entity = new HttpEntity<>(params, headers);

		RestTemplate rt = new RestTemplate();

		ResponseEntity<Building> response = rt.exchange(
			url,
			HttpMethod.POST,
			entity,
			Building.class
		);

		Building body = response.getBody();
		log.info("response = {}", body);

		return ResponseEntity.ok().body("저장 성공");
	}

	private static Long getUserId(HttpSession request) {
		HttpSession session = request;
		User user = (User) session.getAttribute(SessionConst.LOGIN_MEMBER);
		return user.getUserId();
	}


	@PostMapping("file/{buildingId}/{floorNum}")
	public ResponseEntity<?> addPartFloor(@PathVariable("buildingId") int buildingId,
											@PathVariable("floorNum") int floorNum,
											@RequestParam("updateFile") MultipartFile updateFile) throws IOException {

		byte[] floorData = updateFile.getBytes();
		fileService.addPartFloor(buildingId, floorNum, floorData);

		return ResponseEntity.ok().body("수정 성공");
	}



	/**
	 * 건물 리스트 조회
	 * userId를 직접 받는게 아니라 세션값에서 꺼내서 쓸것임
	 * @return 유저가 생성한 건물 리스트
	 * 여기서 건물 - 층 까지 넘겨주면 어떨까?
	 * 굳이 2번 요청을 보낼 필요가 있나?
	 *
	 * 이거는 건물만 보내는 방법
	 */
	@GetMapping("file/list")
	public ResponseEntity<?> BuildingList(HttpServletRequest request) {

		Long userId = getUserId(request.getSession(false));

		List<BuildingResponse> buildingList = fileService.buildingList(userId);
		log.info("buildingList = {}", buildingList);

		return ResponseEntity.ok().body(buildingList);
	}

	/**
	 *  건물정보 조회
	 * @param buildingId
	 * @return 층 리스트
	 */
	@GetMapping("file/{buildingId}/list")
	public ResponseEntity<?> floorList(@PathVariable("buildingId") int buildingId) {

		List<FloorInfo> floorList = fileService.floorList(buildingId);
		log.info("floorList = {}", floorList);

		return ResponseEntity.ok().body(floorList);
	}


	/**
	 * 건물 선택시 파일 다운로드
	 * @return 건물 모델파일
	 */
	@GetMapping("file/{buildingId}")
	public ResponseEntity<?> buildingDownload(@PathVariable("buildingId") int buildingId) {

		byte[] buildingData = fileService.buildingDownload(buildingId);

		return ResponseEntity.ok().body(buildingData);
	}

	/**
	 * 층 선택시 파일 다운로드
	 * @return 층 모델파일
	 */
	@GetMapping("file/{buildingId}/{floorNum}")
	public ResponseEntity<?> floorDownload(@PathVariable("buildingId") int buildingId,
											@PathVariable("floorNum") int floorNum) {

		byte[] floor = fileService.floorDownload(buildingId, floorNum);

		return ResponseEntity.ok().body(floor);
	}

	@PutMapping("file/{buildingId}/{floorNum}")
	public ResponseEntity<?> updateFloor(@PathVariable("buildingId")int buildingId, @PathVariable("floorNum")int floorNum,
										@RequestPart("updateFile")MultipartFile updateFile) throws IOException {

		byte[] floorData = updateFile.getBytes();

		fileService.floorUpdate(buildingId,floorNum,floorData);

		return ResponseEntity.ok().body("수정 성공");
	}

	@DeleteMapping("file/{buildingId}/{floorNum}")
	public ResponseEntity<?> deleteFloor(@PathVariable("buildingId")int buildingId,
											@PathVariable("floorNum")int floorNum) {

		fileService.floorDelete(buildingId,floorNum);
		return ResponseEntity.ok().body("삭제 성공");
	}
}
