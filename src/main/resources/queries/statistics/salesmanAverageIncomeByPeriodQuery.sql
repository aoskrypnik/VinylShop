SELECT salesman_tab_num, (SUM(c.overall_sum * ((100 - c.check_discount) * 0.01)) - (SELECT SUM(r.purchase_price))) / COUNT(*) AS avg_income
FROM cheq c
         INNER JOIN record r on c.check_num = r.check_num
WHERE date_time BETWEEN ? AND ?
GROUP BY salesman_tab_num;