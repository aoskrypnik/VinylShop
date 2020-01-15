SELECT band_alias
FROM artist2band
WHERE artist_alias = ?
AND exit_date IS NOT NULL