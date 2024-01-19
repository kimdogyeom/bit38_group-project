package bit38_7.MapConvertor.repository.file;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
	public int saveFile(int userId, String buildingName, byte[] buildingFacade, int buildingCount) {
		String sql = "insert into building_table(fk_user_id, building_name, building_facede, building_floor) values(:userId, :buildingName, :buildingFacade, :buildingCount)";
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("userId", userId);
			params.put("buildingName", buildingName);
			params.put("buildingFacade", buildingFacade);
			params.put("buildingCount", buildingCount);

			SqlParameterSource param = new BeanPropertySqlParameterSource(params);

			KeyHolder keyHolder = new GeneratedKeyHolder();
			template.update(sql, param, keyHolder);

			int buildingId = keyHolder.getKey().intValue();
			return buildingId;
		}catch (DataAccessException e){
			log.info("error = {}", e.getMessage());
			return -1;
		}
	}
}
