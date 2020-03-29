SELECT SUM(c.sum_with_discount) AS statistics_res
FROM cheq c
WHERE date_time BETWEEN ? AND ?;