SELECT *
FROM composer
WHERE (:country::CHAR(2) IS NULL OR country = :country)
AND (:activity_start::DATE IS NULL OR activity_start >= :activity_start)
AND (:activity_end::DATE IS NULL OR activity_end <= :activity_end)
