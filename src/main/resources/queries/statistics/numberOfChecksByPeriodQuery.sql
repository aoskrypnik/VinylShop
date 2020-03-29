SELECT COUNT(*) AS statistics_res
FROM cheq
WHERE date_time BETWEEN ? AND ?;