UPDATE artist
SET artist_alias = ?,
    activity     = ?,
    country      = ?,
    artist_name  = ?,
    birth_date   = ?,
    death_date   = ?
WHERE artist_alias = ?;