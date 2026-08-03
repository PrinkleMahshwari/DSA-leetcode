# Write your MySQL query statement below
SELECT DiSTINCT author_id AS id
FROM Views
WHERE author_id = viewer_id
ORDER BY id ASC;
