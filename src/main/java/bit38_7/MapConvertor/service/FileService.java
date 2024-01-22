package bit38_7.MapConvertor.service;

import bit38_7.MapConvertor.dto.BuildingInfo;
import bit38_7.MapConvertor.dto.BuildingResponse;
import bit38_7.MapConvertor.dto.FloorInfo;
import bit38_7.MapConvertor.repository.file.JdbcFileRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

	private final JdbcFileRepository jdbcFileRepository;

	/**
	 * 건물 저장
	 * @param userId 유저아이디
	 * @param buildingInfo 건물정보 (건물이름, 층수)
	 * @param fileData 건물사진
	 * @return 건물아이디
	 */
	public int buildingSave(int userId,BuildingInfo buildingInfo, byte[] fileData) {
		return jdbcFileRepository.saveBuilding(userId, buildingInfo.getBuildingName(), fileData, buildingInfo.getBuildingCount());
	}

	/**
	 * 층 저장
	 * @param buildingId 건물아이디
	 * @param floorNum 층번호
	 * @param floorData 층데이터
	 */
	public void floorSave(int buildingId,int floorNum ,byte[] floorData) {
		jdbcFileRepository.saveFloor(buildingId, floorNum, floorData);
	}


	/**
	 * 건물 리스트 조회
	 * @param userId 유저아이디
	 */
	public List<BuildingResponse> buildingList(Long userId) {
		return jdbcFileRepository.userBuildingList(userId);
	}

	/**
	 * 건물 조회
	 * @param buildingId 건물아이디
	 */
	public BuildingResponse building(int buildingId) {
//		return jdbcFileRepository.userBuildingList2(buildingId);
		return null;

	}

	/**
	 * 건물 사진 조회
	 * @param buildingId 건물아이디
	 */
	public byte[] buildingDownload(int buildingId) {
		return jdbcFileRepository.findBuildingFile(buildingId);
	}

	public List<FloorInfo> floorList(int buildingId) {
		return jdbcFileRepository.findFloorList(buildingId);
	}

	public byte[] floorDownload(int buildingId, int floorNum) {
		return jdbcFileRepository.findFloorFile(buildingId, floorNum);
	}
}
