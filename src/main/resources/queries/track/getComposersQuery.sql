SELECT composer_name
FROM composer
WHERE composer_name IN
    (SELECT composer_name
     FROM track2composer
     WHERE track_catalog_num = ?);