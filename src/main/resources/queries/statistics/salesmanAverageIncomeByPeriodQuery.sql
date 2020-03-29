SELECT salesman_tab_num, (SUM(sum_with_discount) / COUNT(*)) AS statistics_res
FROM cheq
WHERE date_time BETWEEN ? AND ?
GROUP BY salesman_tab_num;