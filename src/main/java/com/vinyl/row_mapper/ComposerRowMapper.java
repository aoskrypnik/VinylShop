package com.vinyl.row_mapper;

import com.vinyl.model.Composer;
import com.vinyl.model.Track;
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

	@Value("${sql.get.tracks.query.path}")
	private String getTracksQueryPath;

	@Resource
	private RowMapper<Track> trackRowMapper;
	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public Composer mapRow(ResultSet resultSet, int i) throws SQLException {
		String composerName = resultSet.getString("composer_name");
		List<Track> tracks = getTracksByComposerName(composerName);
		return Composer.builder()
				.name(composerName)
				.country(resultSet.getString("country"))
				.activityStart(resultSet.getDate("activity_start"))
				.activityEnd(resultSet.getDate("activity_end"))
				.tracks(tracks)
				.build();
	}

	private List<Track> getTracksByComposerName(String composerName) {
		String getTracksQuery = QuerySupplier.getQuery(getTracksQueryPath);
		return jdbcTemplate.query(getTracksQuery, new Object[]{composerName}, trackRowMapper);
	}

}
