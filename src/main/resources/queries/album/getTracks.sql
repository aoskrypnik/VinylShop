SELECT *
FROM track
WHERE catalog_num IN
    (SELECT track_catalog_num
     FROM track2album
     WHERE album_catalog_num = ?)