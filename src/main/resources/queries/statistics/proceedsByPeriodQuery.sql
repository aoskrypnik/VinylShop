SELECT SUM(c.overall_sum * ((100 - c.check_discount) * 0.01)) AS statistics_res
FROM cheq c
WHERE date_time BETWEEN ? AND ?;