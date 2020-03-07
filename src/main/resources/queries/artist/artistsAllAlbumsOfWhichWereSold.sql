SELECT *
FROM artist ar
WHERE NOT EXISTS
    (SELECT *
     FROM record
     WHERE check_num IS NULL
       AND release_bar_code IN
           (SELECT bar_code
            FROM release
            WHERE album_catalog_num IN
                  (SELECT al.catalog_num
                   FROM album al
                   WHERE 2 < (SELECT COUNT (*)
                              FROM track2album
                              WHERE album_catalog_num=al.catalog_num
                                AND track_catalog_num IN
                                    (SELECT track_catalog_num
                                     FROM artist2track att
                                     WHERE att.artist_alias=ar.artist_alias
                                       AND NOT featuring)))))
AND EXISTS
    (SELECT *
     FROM record
     WHERE release_bar_code IN
           (SELECT bar_code
            FROM release
            WHERE album_catalog_num IN
                  (SELECT al.catalog_num
                   FROM album al
                   WHERE 2 < (SELECT COUNT (*)
                              FROM track2album
                              WHERE album_catalog_num=al.catalog_num
                                AND track_catalog_num IN
                                    (SELECT track_catalog_num
                                     FROM artist2track att
                                     WHERE att.artist_alias=ar.artist_alias
                                       AND NOT featuring)))))