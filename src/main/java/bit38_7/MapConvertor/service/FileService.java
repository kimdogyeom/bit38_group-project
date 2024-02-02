package bit38_7.MapConvertor.service;

import bit38_7.MapConvertor.dto.*;
import bit38_7.MapConvertor.repository.file.JdbcFileRepository;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

	private final JdbcFileRepository jdbcFileRepository;

	/**
	 * 건물 저장 & 층 데이터 저장
	 */
	@Transactional
	public void fileSave(int userId, BuildingInfo buildingInfo,BuildingRenderResponse response) {

		// 건물 저장 성공 하면 return 으로 빌딩 아이디 획득
		int buildingId = jdbcFileRepository.saveBuilding(userId, buildingInfo.getBuildingName(),
				getDecodeByte(response.getBuildingData()), buildingInfo.getBuildingCount());

		Map<Integer, String> floorDataMap = response.getFloorData();
		Map<Integer, String> floorMetaData = response.getMetaData();

		// 빌딩 아이디 확인후 관련 층 데이터를 저장
		for(Integer floorKey : floorDataMap.keySet()) {
			jdbcFileRepository.saveFloor(buildingId, floorKey,
					getDecodeByte(floorDataMap.get(floorKey)),
					getDecodeByte(floorMetaData.get(floorKey)));

			// TODO 건물파일 생성 후 잘 받는지 로그찍어본 것 확인 후 지우기
			log.info("floorDataMap.get(floorKey) = {}", floorDataMap.get(floorKey));
			log.info("floorMetaData.get(floorKey) = {}", floorMetaData.get(floorKey));
		}

	}

	/**
	 * 층 정보 수정
	 * @param buildingId 빌딩 아이디
	 * @param floorNum 층 번호
	 * @param jsonData 변경 할 층 데이터
	 */
	public void floorUpdate(int buildingId, int floorNum, byte[] jsonData) {
		jdbcFileRepository.updateFloor(buildingId, floorNum, jsonData);
	}

	/**
	 * 층(데아터) 삭제
	 * @param buildingId 빌딩 아이디
	 * @param floorNum 삭제 할 층
	 */
	public void floorDelete(int buildingId,int floorNum) {
		jdbcFileRepository.deleteFloor(buildingId, floorNum);
	}


	/**
	 * 건물 리스트 조회
	 * @param userId 유저 아이디
	 */
	public List<BuildingResponse> buildingList(int userId) {
		return jdbcFileRepository.userBuildingList(userId);
	}

	/**
	 * 건물 모델 다운로드
	 * @param buildingId 건물아이디
	 */
	public byte[] buildingDownload(int buildingId) {
		return jdbcFileRepository.findBuildingFile(buildingId);
	}

	/**
	 * 층 리스트 조회
	 * @param buildingId 빌딩 아이디
	 */
	public List<FloorInfo> floorList(int buildingId) {
		return jdbcFileRepository.findFloorList(buildingId);
	}

	/**
	 * 층 데이터 다운로드
	 * @param buildingId 빌딩 아이디
	 * @param floorNum 원하는 층
	 * */
	public ModelResponse floorDownload(int buildingId, int floorNum) {
		return jdbcFileRepository.findFloorFile(buildingId, floorNum);
	}

	public void addPartFloor(int buildingId, int floorNum,floorRenderResponse response) {

		    byte[] floorBytes = getDecodeByte(response.getFloorData());
       		byte[] floorMetaBytes = getDecodeByte(response.getMetaData());


//		jdbcFileRepository.updateFloor(buildingId, floorNum, floorBytes, floorMetaBytes);
	}

	/**
	 *건물 삭제 및 층 데이터 삭제
	 * @param buildingId 빌딩 아이디
	 */
	@Transactional
	public void buildingDelete(int buildingId) {
		jdbcFileRepository.deleteFloorAll(buildingId);
		jdbcFileRepository.deleteBuilding(buildingId);
	}

	/**
	 *빌딩 이름 조회
	 * @param buildingId 빌딩 아이디
	 */
	public String buildingName(int buildingId) {
		return jdbcFileRepository.findBuildingName(buildingId);
	}

	private static byte[] getDecodeByte(String encodingData) {
		return Base64.getDecoder().decode(encodingData);
	}

}
