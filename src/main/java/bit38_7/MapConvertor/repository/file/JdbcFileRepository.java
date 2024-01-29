package bit38_7.MapConvertor.repository.file;

import bit38_7.MapConvertor.dto.BuildingResponse;
import bit38_7.MapConvertor.dto.FloorInfo;
import java.util.List;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class JdbcFileRepository implements FileRepository {

	private final NamedParameterJdbcTemplate template;

	public JdbcFileRepository(DataSource dataSource) {
		this.template = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public int saveBuilding(int userId, String buildingName, byte[] buildingFacade, int buildingCount) {
		String sql = "insert into building_table(fk_user_id, building_name, building_facade, building_floor) values(:userId, :buildingName, :buildingFacade, :buildingCount)";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("userId", userId);
			params.addValue("buildingName", buildingName);
			params.addValue("buildingFacade", buildingFacade);
			params.addValue("buildingCount", buildingCount);

			KeyHolder keyHolder = new GeneratedKeyHolder();
			template.update(sql, params, keyHolder);

			int buildingId = keyHolder.getKey().intValue();
			return buildingId;
		} catch (DataAccessException e) {
			log.info("error = {}", e.getMessage());
			return -1;
		}
	}

	@Override
	public void saveFloor(int buildingId, int floorNum, byte[] floorData) {
		String sql = "insert into floor_table(building_id, floor_num, floor_file_data, update_date) values(:buildingId, :floorNum, :floorData, CURRENT_DATE())";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("buildingId", buildingId);
			params.addValue("floorNum", floorNum);
			params.addValue("floorData", floorData);

			template.update(sql, params);
		} catch (DataAccessException e) {
			log.info("error = {}", e.getMessage());
		}
	}



	@Override
	public List<BuildingResponse> userBuildingList(Long userId) {
		String sql = "select building_id, building_name, building_floor from building_table where fk_user_id = :userId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("userId", userId);

			return template.query(sql, params, buildingRowMapper());
		} catch (DataAccessException e) {
			log.info("error = {}", e.getMessage());
			return null;
		}
	}

	@Override
	public byte[] findBuildingFile(int buildingId) {
		String sql = "select building_facade from building_table where building_id = :buildingId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("buildingId", buildingId);

			return template.queryForObject(sql, params, byte[].class);
		} catch (DataAccessException e) {
			log.info("error = {}", e.getMessage());
			return null;
		}
	}

	@Override
	public List<FloorInfo> findFloorList(int buildingId) {
		String sql = "select floor_num, floor_file_data, update_date from floor_table where building_id = :buildingId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("buildingId", buildingId);

			return template.query(sql, params, floorRowMapper());
		} catch (DataAccessException e) {
			log.info("error = {}", e.getMessage());
			return null;
		}

	}

	@Override
	public byte[] findFloorFile(int buildingId, int floorNum) {
		String sql = "select floor_file_data from floor_table where building_id = :buildingId and floor_num = :floorNum";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("buildingId", buildingId);
			params.addValue("floorNum", floorNum);

			return template.queryForObject(sql, params, byte[].class);
		} catch (DataAccessException e) {
			log.info("error = {}", e.getMessage());
			return null;
		}
	}

	@Override
	public void updateFloor(int buildingId,int floorNum, byte[] floorData) {
		String sql = "update floor_table set floor_file_data =:floorData where floor_num = :floorNum and building_id = :buildingId";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("buildingId", buildingId);
			params.addValue("floorData", floorData);
			params.addValue("floorNum", floorNum);

			template.update(sql, params);
		} catch (DataAccessException e) {
			log.info("error = {}", e.getMessage());
		}
	}

	@Override
	public void deleteFloor(int buildingId,int floorNum) {
		String sql = "update floor_table set floor_file_data =:floorDeleteData where building_id = :buildingId and floor_num = :floorNum";
		try {
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("floorDeleteData", null);
			params.addValue("buildingId", buildingId);
			params.addValue("floorNum", floorNum);

			template.update(sql, params);
		}catch (DataAccessException e) {
			log.info("error = {}", e.getMessage());
		}
	}

	@Override
	public int findById(int userId, int buildingId) {
		String sql = "SELECT COUNT(*) FROM building_table WHERE building_id =:building AND fk_user_id =:userId;";
		try {
			MapSqlParameterSource param = new MapSqlParameterSource();
			param.addValue("userId", userId);
			param.addValue("building", buildingId);
			return  template.queryForObject(sql, param, Integer.class);
		} catch (DataAccessException e) {
			log.info("error = {}", e.getMessage());
			return 0;
		}
	}


	private RowMapper<FloorInfo> floorRowMapper() {
		return BeanPropertyRowMapper.newInstance(FloorInfo.class);
	}

	private RowMapper<BuildingResponse> buildingRowMapper() {
		return BeanPropertyRowMapper.newInstance(BuildingResponse.class);
	}

}

