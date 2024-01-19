package bit38_7.MapConvertor.repository.file;


public interface FileRepository {

	int saveFile(int userId, String buildingName, byte[] buildingFacade, int buildingCount);

}
