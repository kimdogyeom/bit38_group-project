package bit38_7.MapConvertor.service;

import bit38_7.MapConvertor.dto.BuildingInfo;
import bit38_7.MapConvertor.repository.file.JdbcFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

	private final JdbcFileRepository jdbcFileRepository;

	public int buildingSave(int userId,BuildingInfo buildingInfo, byte[] fileData) {

		int buildingId = jdbcFileRepository.saveFile(userId, buildingInfo.getBuildingName(), fileData,
			buildingInfo.getBuildingCount());
		return buildingId;
	}

	public void floorSave(int buildingId, byte[] bytes) {

	}
}
