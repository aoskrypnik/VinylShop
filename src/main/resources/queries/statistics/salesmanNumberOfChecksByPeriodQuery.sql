SELECT salesman_tab_num, count(*) AS number_of_checks
FROM cheq
WHERE date_time BETWEEN ? AND ?
GROUP BY salesman_tab_num;