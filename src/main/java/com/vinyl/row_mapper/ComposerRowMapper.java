package com.vinyl.row_mapper;

import com.vinyl.model.Composer;
import com.vinyl.utils.QuerySupplier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class ComposerRowMapper implements RowMapper<Composer> {

	@Value("${sql.composer.get.tracks.query.path}")
	private String getTracksQueryPath;

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public Composer mapRow(ResultSet resultSet, int i) throws SQLException {
		String composerName = resultSet.getString("composer_name");
		List<String> tracks = getTracksByComposerName(composerName);
		return Composer.builder()
				.composerName(composerName)
				.composerCountryCode(resultSet.getString("country"))
				.activityStart(resultSet.getDate("activity_start"))
				.activityEnd(resultSet.getDate("activity_end"))
				.trackIds(tracks)
				.build();
	}

	private List<String> getTracksByComposerName(String composerName) {
		String getTracksQuery = QuerySupplier.getQuery(getTracksQueryPath);
		return jdbcTemplate.queryForList(getTracksQuery, new Object[]{composerName}, String.class);
	}

}
