SELECT artist_alias
FROM artist2track
WHERE track_catalog_num = ? AND NOT featuring;