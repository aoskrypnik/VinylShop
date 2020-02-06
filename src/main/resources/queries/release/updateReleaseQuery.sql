UPDATE release
SET album_catalog_num =?,
    country           =?,
    release_date      =?,
    record_size       =?,
    record_speed      =?,
    copies_cnt        =?,
    repress           =?,
    label             =?
WHERE bar_code =?;