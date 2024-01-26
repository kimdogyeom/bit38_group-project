package bit38_7.MapConvertor.repository.file;


import bit38_7.MapConvertor.dto.BuildingResponse;
import bit38_7.MapConvertor.dto.FloorInfo;
import java.util.List;

public interface FileRepository {

	int saveBuilding(int userId, String buildingName, byte[] buildingFacade, int buildingCount);

	void saveFloor(int buildingId, int floorNum, byte[] floorData);

	List<BuildingResponse> userBuildingList(Long userId);

	byte[] findBuildingFile(int buildingId);

	List<FloorInfo> findFloorList(int buildingId);

	byte[] findFloorFile(int buildingId, int floorNum);

	void updateFloor(int UserId,int floorNum, byte[] floorData);

	void deleteFloor(int userId,int floorNum);

	int FindById(int userId, int buildingId);
}
