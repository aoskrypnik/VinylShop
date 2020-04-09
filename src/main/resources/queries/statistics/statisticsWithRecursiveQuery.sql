WITH RECURSIVE stat(month_id, start_date, end_date, check_num, income, avg_check, proceeds) AS
                   (SELECT 1,
                           ?::timestamp,
                           ?::timestamp + interval '1 month' - interval '1 day',
                           (SELECT COUNT(*) AS check_num
                            FROM cheq
                            WHERE date_time BETWEEN ?::timestamp AND (?::timestamp + interval '1 month' - interval '1 day')),
                           (SELECT SUM(r.sell_price * ((100 - c.check_discount) * 0.01) - r.purchase_price) AS income
                            FROM cheq c
                                     INNER JOIN record r on c.check_num = r.check_num
                            WHERE c.date_time BETWEEN ?::timestamp AND (?::timestamp + interval '1 month' - interval '1 day')),
                           (SELECT (SUM(sum_with_discount) / COUNT(*)) AS avg_check
                            FROM cheq
                            WHERE date_time BETWEEN ?::timestamp AND (?::timestamp + interval '1 month' - interval '1 day')),
                           (SELECT SUM(c.sum_with_discount) AS statistics_res
                            FROM cheq c
                            WHERE date_time BETWEEN ?::timestamp AND (?::timestamp + interval '1 month' - interval '1 day'))

                    UNION
                    SELECT month_id + 1,
                           start_date + interval '1 month',
                           start_date + interval '2 month' - interval '1 day',
                           (SELECT COUNT(*) AS statistics_res
                            FROM cheq
                            WHERE date_time BETWEEN start_date + interval '1 month' AND start_date + interval '2 month'),
                           (SELECT SUM(r.sell_price * ((100 - c.check_discount) * 0.01) - r.purchase_price) AS income
                            FROM cheq c
                                     INNER JOIN record r on c.check_num = r.check_num
                            WHERE c.date_time BETWEEN start_date + interval '1 month' AND start_date + interval '2 month'),
                           (SELECT (SUM(sum_with_discount) / COUNT(*)) AS avg_check
                            FROM cheq
                            WHERE date_time BETWEEN start_date + interval '1 month' AND start_date + interval '2 month'),
                           (SELECT SUM(c.sum_with_discount) AS statistics_res
                            FROM cheq c
                            WHERE date_time BETWEEN start_date + interval '1 month' AND start_date + interval '2 month')
                    FROM stat
                    WHERE month_id < 12)
SELECT *
FROM stat
ORDER BY month_id ASC;