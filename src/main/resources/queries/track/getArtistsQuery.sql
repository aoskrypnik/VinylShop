SELECT artist_alias
FROM performance
WHERE track_catalog_num = ? AND NOT featuring);