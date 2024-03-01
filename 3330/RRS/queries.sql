.mode csv
CREATE TABLE Train(TrainNumber INT,TrainName TEXT, Premium_Fair INT,General_Fair INT, Sorce_Station TEXT, Destination_Station TEXT, Available_On TEXT);
.import Train.csv Train

CREATE TABLE Passenger(FirstName TEXT,LastName TEXT,"address" TEXT,city TEXT,county TEXT,phone2 TEXT,SSN TEXT,bdate DATE);
INSERT INTO Passenger (FirstName, LastName, "address", city, county, phone2, SSN, bdate) VALUES 
('Josephine', 'Darakjy', '4 B Blue Ridge Blvd', 'Brighton', 'Livingston', '810-374-9840', '240471168', '1975-11-01'),
('Art', 'Venere', '8 W Cerritos Ave #54', 'Bridgeport', 'Gloucester', '605-264-4130', '285200976', '1982-11-13'),
('Lenna', 'Paprocki', '639 Main St', 'Anchorage', 'Anchorage', '907-921-2010', '309323096', '1978-08-09'),
('Donette', 'Foller', '34 Center St', 'Hamilton', 'Butler', '513-549-4561', '272610795', '1990-06-11'),
('Simona', 'Morasca', '3 Mcauley Dr', 'Ashland', 'Ashland', '419-800-6759', '250951162', '1994-08-15'),
('Mitsue', 'Tollner', '7 Eads St', 'Chicago', 'Cook', '773-924-8565', '272913578', '1984-07-04'),
('Leota', 'Dilliard', '7 W Jackson Blvd', 'San Jose', 'Santa Clara', '408-813-1105', '268682534', '1991-05-09'),
('Sage', 'Wieser', '5 Boston Ave #88', 'Sioux Falls', 'Minnehaha', '605-794-4895', '310908858', '1982-02-25'),
('Kris', 'Marrier', '228 Runamuck Pl #2808', 'Baltimore', 'Baltimore City', '410-804-4694', '322273872', '1956-04-04'),
('Minna', 'Amigon', '2371 Jerrold Ave', 'Kulpsville', 'Montgomery', '215-422-8694', '256558303', '1995-09-09'),
('Abel', 'Maclead', '37275 St Rt 17m M', 'Middle Island', 'Suffolk', '631-677-3675', '302548590', '1960-11-05'),
('Kiley', 'Caldarera', '25 E 75th St #69', 'Los Angeles', 'Los Angeles', '310-254-3084', '284965676', '1981-05-09'),
('Graciela', 'Ruta', '98 Connecticut Ave Nw', 'Chagrin Falls', 'Geauga', '440-579-7763', '277292710', '1982-02-25'),
('Cammy', 'Albares', '56 E Morehead St', 'Laredo', 'Webb', '956-841-7216', '331160133', '1956-04-04'),
('Mattie', 'Poquette', '73 State Road 434 E', 'Phoenix', 'Maricopa', '605-953-6360','331293204','1995-09-09'),
('Meaghan','Garufi','69734 E Carrillo St','Mc Minnville','Warren','931-235-7959','290123298','1960-11-02'),
('Gladys','Rim','322 New Horizon Blvd','Milwaukee','Milwaukee','414-377-2880','286411536','1991-05-09'),
('Yuki','Whobrey','1 State Route 27','Taylor','Wayne','313-341-4470','294860856','1985-02-25'),
('Fletcher','Flosi','394 Manchester Blvd','Rockford','Winnebago','815-426-5657','317434088','1961-04-04');

CREATE TABLE Booked(PassengerSSN TEXT,TrainNumber TEXT,Ticket_Type TEXT,"Status" TEXT);
.import booked-1.csv Booked

CREATE TABLE TrainStatus(TrainDate TEXT, TrainName TEXT, PremiumSeatsAvailable INT , GenSeatsAvailable INT, PremiumSeatsOccupied INT,GenSeatsOccupied INT);
.import Train_status.csv TrainStatus


.mode columns
-- Input a passenger’s last name and first name and retrieve all trains they are booked on.
SELECT DISTINCT TrainNumber FROM Booked WHERE PassengerSSN=(SELECT SSN FROM Passenger WHERE "FirstName"="Minna" AND "LastName"="Amigon");

-- Input the Day and list the passengers travelling on that day with confirmed tickets. ( 2 different interpretations )
-- SELECT FirstName, LastName FROM Passenger WHERE SSN IN (SELECT DISTINCT PassengerSSN FROM Booked WHERE "Status"="Booked" AND TrainNumber IN (SELECT TrainNumber FROM Train WHERE Available_On="Monday"));
SELECT FirstName, LastName FROM Passenger WHERE SSN IN (SELECT DISTINCT PassengerSSN FROM Booked WHERE "Status"="Booked" AND TrainNumber IN (SELECT DISTINCT TrainNumber from Train where TrainName IN (select distinct TrainName from TrainStatus where TrainDate="20/02/2022")));

-- User input the age of the passenger (50 to 60) and display the train information (Train Number, Train Name, Source and Destination) and passenger information (Name, Address, Category, ticket status) of passengers who are between the ages of 50 to 60. 
SELECT DISTINCT Booked.TrainNumber, Train.TrainName, Train.Sorce_Station, Train.Destination_Station, Passenger.FirstName, Passenger.LastName, Passenger.address, Booked.Ticket_Type, Booked.Status
FROM Booked
JOIN Train ON Booked.TrainNumber = Train.TrainNumber
JOIN Passenger ON Booked.PassengerSSN= Passenger.SSN
WHERE strftime('%Y', 'now') - strftime('%Y', Passenger.bdate) BETWEEN 50 AND 60;

-- List train name, day and number of passenger on that train. 
select TrainName, TrainDate , PremiumSeatsOccupied+GenSeatsOccupied  AS Passengers from TrainStatus ; 

-- Enter a train name and retrieve all the passengers with confirmed status traveling in that train.
select FirstName, LastName FROM Passenger WHERE SSN IN(select DISTINCT PassengerSSN FROM Booked WHERE Status="Booked" AND TrainNumber IN (select DISTINCT TrainNumber FROM Train WHERE TrainName="Flying Scottsman") );

-- List passengers that are waitlisted including the name of the train.
select FirstName, LastName FROM Passenger WHERE SSN IN(select distinct PassengerSSN FROM Booked WHERE Status="WaitL");

-- List passengers that have '605' phone area code in descending order.
select FirstName, LastName FROM Passenger WHERE phone2 LIKE "605%" ORDER BY phone2 DESC;

-- List name of passengers that are traveling on Thursdays in ascending order.
select DISTINCT FirstName, LastName FROM Passenger WHERE SSN IN (select DISTINCT PassengerSSN FROM Booked WHERE Status="Booked" AND TrainNumber IN (select DISTINCT TrainNumber FROM Train WHERE Available_On="Thursday")) ORDER BY FirstName ASC;


