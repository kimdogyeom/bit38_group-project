package bit38_7.MapConvertor.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RoomSerchDTO {

	int buildingId;
	int floor;
	String roomName;

}
