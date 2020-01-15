SELECT *
FROM album
WHERE catalog_num IN
    (SELECT album_catalog_num
     FROM track2album
     WHERE track_catalog_num = ?);