CREATE TABLE IF NOT EXISTS Employee (
    Fname TEXT,
    Minit TEXT,
    Lname TEXT,
    Ssn INT,
    Bdate DATE,
    Address TEXT,
    Sex CHAR,
    Salary INT,
    Super_ssn INT,
    Dno INT
);

CREATE TABLE IF NOT EXISTS Department (
    Dname TEXT,
    Dnumber INT,
    Mgr_ssn INT,
    Mgr_start_date DATE,
);

CREATE TABLE IF NOT EXISTS Dept_Locations(
    Dnumber INT,
    Dlocation TEXT,
);

CREATE TABLE IF NOT EXISTS Project (
    Pname TEXT,
    Pnumber INT,
    Plocation TEXT,
    Dnum INT
);

CREATE TABLE IF NOT EXISTS Works_On (
    Essn INT,
    Pno INT,
    Hours INT
);

CREATE TABLE IF NOT EXISTS Dependant (
    Essn INT,
    Dependant_name TEXT,
    Sex CHAR,
    Bdate DATE,
    Relationship TEXT
);



-- creating airport database from figure 5.8
CREATE TABLE IF NOT EXISTS AIRPORT (
    Airport_code TEXT,
    Name TEXT,
    City TEXT,
    State TEXT,
    Country TEXT,
    PRIMARY KEY (Airport_code)
);

CREATE TABLE IF NOT EXISTS FLIGHT (
    Flight_number INT,
    Airline TEXT,
    Weekdays TEXT,
    PRIMARY KEY (Flight_number)
);

CREATE TABLE IF NOT EXISTS FLIGHT_LEG (
    Flight_number INT,
    Leg_number INT,
    Departure_airport_code TEXT,
    Arrival_airport_code TEXT,
    Scheduled_departure_time TIME,
    Scheduled_arrival_time TIME,
    PRIMARY KEY (Flight_number, Leg_number)
);

create table if not exists Leg_Instance (
    Flight_number INT,
    Leg_number INT,
    Date DATE,
    Number_of_available_seats INT,
    Airplane_id INT,
    Scheduled_departure_time TIME,
    Scheduled_arrival_time TIME,
    Arrival_airport_code TEXT,
    Departure_airport_code TEXT,
    PRIMARY KEY (Flight_number, Leg_number, Date)
);


create table if not exists FARE (
    Flight_number INT,
    Fare_code INT,
    Restrictions TEXT,
    PRIMARY KEY (Flight_number, Fare_code)
);

create table if not exists Airplane_type (
    Company TEXT,
    Airplane_type_name TEXT,
    Max_seats INT
    PRIMARY KEY (Airplane_id)
);

create table if not exists Can_Land(
    Airplane_type_name TEXT,
    Airport_code TEXT,
    PRIMARY KEY (Airplane_type_name, Airport_code)
);

create table if not exists Airplane (
    Airplane_id INT,
    Total_number_of_seats INT,
    Airplane_type TEXT
    PRIMARY KEY (Airplane_id)
);

create table if not exists Seat_Reservation(
    Flight_number INT,
    Leg_number INT,
    Date DATE,
    Seat_number INT,
    Customer_name TEXT,
    Customer_phone TEXT,
    PRIMARY KEY (Flight_number, Leg_number, Date, Seat_number)
);
