package bit38_7.MapConvertor.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RoomSerchResult {

	int floor;
	String objectName;

	public RoomSerchResult(int floor, String objectName) {
		this.floor = floor;
		this.objectName = objectName;
	}
}
