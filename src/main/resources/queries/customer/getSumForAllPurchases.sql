SELECT sum(overall_sum)
FROM cheq
WHERE check_num IN (SELECT check_num
                    FROM cheq
                    WHERE customer_num = ?)