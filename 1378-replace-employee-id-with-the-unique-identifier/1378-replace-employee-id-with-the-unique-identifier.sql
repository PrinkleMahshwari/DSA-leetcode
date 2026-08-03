# Write your MySQL query statement below
SELECT
    empU.unique_id,
    emp.name
FROM Employees emp
LEFT JOIN EmployeeUNI empU
ON emp.id = empU.id;
