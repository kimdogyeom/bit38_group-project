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

//	/**
//	 * 건물 저장
//	 * @param userId 유저아이디
//	 * @param buildingInfo 건물정보 (건물이름, 층수)
//	 * @param fileData 건물사진
//	 * @return 건물아이디
//	 */
//	public int buildingSave(int userId,BuildingInfo buildingInfo, byte[] fileData) {
//		return jdbcFileRepository.saveBuilding(userId, buildingInfo.getBuildingName(), fileData, buildingInfo.getBuildingCount());
//	}

	/**
	 * 층 저장
	 * @param buildingId 건물아이디
	 * @param floorNum 층번호
	 * @param floorData 층데이터
	 */
	public void floorSave(int buildingId,int floorNum ,byte[] floorData, byte[]jsonData) {
		jdbcFileRepository.saveFloor(buildingId, floorNum, floorData, jsonData);
	}

	/**
	 * 층 정보 수정
	 * @param floorNum 층 번호
	 */
	public void floorUpdate(int buildingId, int floorNum, byte[] jsonData) {
		jdbcFileRepository.updateFloor(buildingId, floorNum, jsonData);
	}


	public void floorDelete(int buildingId,int floorNum) {
		jdbcFileRepository.deleteFloor(buildingId, floorNum);
	}


	/**
	 * 건물 리스트 조회
	 * @param userId 유저아이디
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

	public List<FloorInfo> floorList(int buildingId) {
		return jdbcFileRepository.findFloorList(buildingId);
	}

	public ModelResponse floorDownload(int buildingId, int floorNum) {
		return jdbcFileRepository.findFloorFile(buildingId, floorNum);
	}

	public void addPartFloor(int buildingId, int floorNum,floorRenderResponse response) {

		    byte[] floorBytes = getDecodeByte(response.getFloorData());
       		byte[] floorMetaBytes = getDecodeByte(response.getMetaData());


//		jdbcFileRepository.updateFloor(buildingId, floorNum, floorBytes, floorMetaBytes);
	}

	@Transactional
	public void buildingDelete(int buildingId) {
		jdbcFileRepository.deleteFloorAll(buildingId);
		jdbcFileRepository.deleteBuilding(buildingId);
	}

	@Transactional
	public void fileSave(int userId, BuildingInfo buildingInfo,BuildingRenderResponse response) {
		int buildingId = jdbcFileRepository.saveBuilding(userId, buildingInfo.getBuildingName(),
				getDecodeByte(response.getBuildingData()), buildingInfo.getBuildingCount());

		Map<Integer, String> floorDataMap = response.getFloorData();
		Map<Integer, String> floorJsonData = response.getMetaData();

		for(Integer floorKey : floorDataMap.keySet()) {
			jdbcFileRepository.saveFloor(buildingId, floorKey,
					getDecodeByte(floorDataMap.get(floorKey)),
					getDecodeByte(floorJsonData.get(floorKey)));

			// TODO 건물파일 생성 후 잘 받는지 로그찍어본 것 확인 후 지우기
			log.info("floorDataMap.get(floorKey) = {}", floorDataMap.get(floorKey));
			log.info("floorJsonData.get(floorKey) = {}", floorJsonData.get(floorKey));
		}

	}

	public String buildingName(int buildingId) {
		return jdbcFileRepository.findBuildingName(buildingId);
	}

	private static byte[] getDecodeByte(String encodingData) {
		return Base64.getDecoder().decode(encodingData);
	}

}
