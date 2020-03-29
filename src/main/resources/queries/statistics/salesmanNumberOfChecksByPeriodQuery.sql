SELECT salesman_tab_num, COUNT(*) AS statistics_res
FROM cheq
WHERE date_time BETWEEN ? AND ?
GROUP BY salesman_tab_num;