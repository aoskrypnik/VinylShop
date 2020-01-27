SELECT*
FROM band
WHERE band_alias IN (SELECT band_alias
                     FROM artist2band
                     WHERE artist_alias = ?
                       AND join_date BETWEEN ? AND ?);