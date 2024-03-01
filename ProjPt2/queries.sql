--7
SELECT lb.Branch_Id, lb.Branch_Name, 
  SUM(CASE WHEN bl.Returned_Date IS NOT NULL THEN 1 ELSE 0 END) AS returned_count, 
  SUM(CASE WHEN bl.Returned_Date IS NULL AND bl.Due_Date >= date('now') THEN 1 ELSE 0 END) AS still_borrowed_count, 
  SUM(CASE WHEN bl.Returned_Date IS NULL AND bl.Due_Date < date('now') THEN 1 ELSE 0 END) AS late_count
FROM LIBRARY_BRANCH lb
LEFT JOIN BOOK_LOANS bl ON lb.Branch_Id = bl.Branch_Id
GROUP BY lb.Branch_Id
ORDER BY lb.Branch_Id;
