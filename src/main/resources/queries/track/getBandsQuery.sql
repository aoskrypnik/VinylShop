SELECT *
FROM band
WHERE band_alias IN
    (SELECT band_alias
     FROM performance
     WHERE track_catalog_num = ? AND NOT featuring);