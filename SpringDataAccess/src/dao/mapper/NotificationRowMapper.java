package dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class NotificationRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int line) throws SQLException {
		NotificationResultSetExtractor extractor = new NotificationResultSetExtractor();
		return extractor.extractData(rs);
	}

}