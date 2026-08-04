# Write your MySQL query statement below
SELECT
    emp.name,
    bo.bonus
FROM Employee emp
LEFT JOIN Bonus bo
ON bo.empId = emp.empId
WHERE bo.bonus < 1000 OR bo.bonus IS NULL;
