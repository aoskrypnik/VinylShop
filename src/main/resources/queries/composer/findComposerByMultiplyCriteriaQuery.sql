SELECT *
FROM composer
WHERE (:country::CHAR(2) is null or country = :country)
and (:activity_start::DATE is null or activity_start >= :activity_start)
and (:activity_end::DATE is null or activity_end <= :activity_end)
