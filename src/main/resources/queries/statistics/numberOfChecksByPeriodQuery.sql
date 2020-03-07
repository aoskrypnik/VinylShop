SELECT count(*) AS number_of_checks
FROM cheq
WHERE date_time BETWEEN ? AND ?;