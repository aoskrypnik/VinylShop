SELECT *
FROM track
WHERE catalog_num IN
    (SELECT track_catalog_num
     FROM performance
     WHERE band_alias = ? AND NOT featuring);