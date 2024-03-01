Question 1:

A)
SELECT Employee.Fname, Employee.Lname
FROM Employee
INNER JOIN Dependant ON Employee.Ssn = Dependant.Essn
WHERE Employee.Fname = Dependant.Dependant_name;

B)
SELECT Employee.Fname, Employee.Lname
FROM Employee
WHERE NOT EXISTS (
    SELECT *
    FROM Project
    WHERE NOT EXISTS (
        SELECT *
        FROM Works_On
        WHERE Works_On.Essn = Employee.Ssn AND Works_On.Pno = Project.Pnumber
    )
);

C)
SELECT AVG(Salary) 
FROM Employee
WHERE Sex = 'F';


D)
SELECT Employee.Lname
FROM Employee, Department
WHERE Employee.Ssn = Department.Mgr_ssn
AND NOT EXISTS (
    SELECT *
    FROM Dependant
    WHERE Dependant.Essn = Employee.Ssn
);


Question 3:
A)
SELECT *
FROM FARE
INNER JOIN FLIGHT ON FARE.Flight_number = FLIGHT.Flight_number
WHERE FLIGHT.Flight_number = 'co197';

B)
SELECT FLIGHT.Flight_number, FLIGHT.Weekdays
FROM FLIGHT_LEG
INNER JOIN FLIGHT ON FLIGHT_LEG.Flight_number = FLIGHT.Flight_number
WHERE FLIGHT_LEG.Departure_airport_code = 'iah' AND FLIGHT_LEG.Arrival_airport_code = 'lax';

C)
SELECT Number_of_available_seats
FROM Leg_Instance
WHERE Flight_number = 'co197' AND Date = '2009-10-09';




Question 5:
A)
SELECT ORDER.Order#, SHIPMENT.Ship_date
FROM ORDER
INNER JOIN SHIPMENT ON ORDER.Order# = SHIPMENT.Order#
WHERE SHIPMENT.Warehouse# = 'W2';

B)
SELECT CUSTOMER.Cname, COUNT(ORDER.Order#) AS No_of_orders, AVG(ORDER.Ord_amt) AS Avg_order_amt
FROM CUSTOMER
INNER JOIN ORDER ON CUSTOMER.Cust# = ORDER.Cust#
GROUP BY CUSTOMER.Cname;

c)
SELECT DISTINCT Order#
FROM SHIPMENT
WHERE Warehouse# IN (
    SELECT WAREHOUSE.Warehouse#
    FROM WAREHOUSE
    WHERE WAREHOUSE.City = 'New York'
)
AND NOT EXISTS (
    SELECT *
    FROM WAREHOUSE
    WHERE WAREHOUSE.City = 'New York'
    AND NOT EXISTS (
        SELECT *
        FROM SHIPMENT
        WHERE SHIPMENT.Order# = SHIPMENT.Order#
        AND SHIPMENT.Warehouse# = WAREHOUSE.Warehouse#
    )
);
