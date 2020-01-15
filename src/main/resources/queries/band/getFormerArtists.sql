SELECT *
FROM artist
WHERE artist_alias IN
    (SELECT artist_alias
     FROM artist2band
     WHERE band_alias = ?
     AND exit_date IS NOT NULL)