SELECT *
FROM artist
WHERE artist_alias IN
    (SELECT artist_alias
     FROM performance
     WHERE track_catalog_num = ? AND featuring);