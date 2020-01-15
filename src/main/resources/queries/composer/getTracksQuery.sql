SELECT catalog_num
FROM track
WHERE catalog_num IN
    (SELECT track_catalog_num
     FROM track2composer
     WHERE composer_name = ?);