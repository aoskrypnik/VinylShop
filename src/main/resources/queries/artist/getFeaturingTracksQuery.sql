SELECT track_catalog_num
FROM artist2track
WHERE artist_alias = ?
AND featuring;