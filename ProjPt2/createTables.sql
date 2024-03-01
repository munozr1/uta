CREATE TABLE PUBLISHER (
    Publisher_Name varchar(255) primary key,
    Phone varchar(255),
    Address varchar(255)
);

CREATE TABLE LIBRARY_BRANCH (
    Branch_Id int primary key,
    Branch_Name varchar(255),
    Branch_Address varchar(255)
);

CREATE TABLE BORROWER (
    Card_No int,
    Name varchar(255) primary key,
    Address varchar(255),
    Phone varchar(255)
);

CREATE TABLE BOOK (
    Book_Id int primary key,
    Title varchar(255),
    Publisher_Name varchar(255),
    Foreign key (Publisher_Name) references PUBLISHER(Publisher_Name)
);

CREATE TABLE BOOK_LOANS (
    Book_Id int,
    Branch_Id int,
    Card_No int,
    Date_Out date primary key,
    Due_Date date,
    Returned_date date,
    Foreign key (Book_Id) references BOOK(Book_Id),
    Foreign key (Branch_Id) references LIBRARY_BRANCH(Branch_Id),
    Foreign key (Card_No) references BORROWER(Card_No)
);

CREATE TABLE BOOK_COPIES (
    Book_Id int,
    Branch_Id int,
    No_Of_Copies int,
    Foreign key (Book_Id) references BOOK(Book_Id),
    Foreign key (Branch_Id) references LIBRARY_BRANCH(Branch_Id)
);

CREATE TABLE BOOK_AUTHORS (
    Book_Id int primary key,
    Author_Name varchar(255),
    Foreign key (Book_Id) references BOOK(Book_Id)
);


-- set mode to csv
.mode csv

-- import data from csv files
.import ./LMSDataset/PUBLISHER.csv PUBLISHER
.import ./LMSDataset/LIBRARY_BRANCH.csv LIBRARY_BRANCH
.import ./LMSDataset/BORROWER.csv BORROWER
.import ./LMSDataset/BOOK.csv BOOK
.import ./LMSDataset/BOOK_LOANS.csv BOOK_LOANS
.import ./LMSDataset/BOOK_COPIES.csv BOOK_COPIES
.import ./LMSDataset/BOOK_AUTHORS.csv BOOK_AUTHORS


