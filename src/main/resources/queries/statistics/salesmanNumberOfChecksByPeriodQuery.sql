SELECT salesman_tab_num, count(*) AS statistics_res
FROM cheq
WHERE date_time BETWEEN ? AND ?
GROUP BY salesman_tab_num;