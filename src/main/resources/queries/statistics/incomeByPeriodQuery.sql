SELECT SUM(r.sell_price * ((100 - c.check_discount) * 0.01) - r.purchase_price) AS statistics_res
FROM cheq c
         INNER JOIN record r on c.check_num = r.check_num
WHERE c.date_time BETWEEN ? AND ?;