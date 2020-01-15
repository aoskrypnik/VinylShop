SELECT *
FROM track
WHERE catalog_num IN
    (SELECT track_catalog_num
     FROM performance
     WHERE artist_alias = ? AND NOT featuring);