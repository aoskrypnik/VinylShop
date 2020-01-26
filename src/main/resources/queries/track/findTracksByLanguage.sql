SELECT *
FROM track
WHERE catalog_num IN (
SELECT  track_catalog_num
FROM track_language WHERE lang_name = ?
);