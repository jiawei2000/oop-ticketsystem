
-- Database: OOP_Project 
drop database OOP_Project;
CREATE DATABASE IF NOT EXISTS OOP_Project;
use OOP_Project; 
 
DROP TABLE IF EXISTS User;
CREATE TABLE User(
	UserID int not null auto_increment,
    Username varchar(30) not null, 
    Password varchar(30) not null, 
    Usertype char(1) not null, -- either 'C', 'M', 'T'
	constraint User_pk primary key (UserID)
);
insert into User values (001, 'userTest', 'password123', 'M');


DROP TABLE IF EXISTS Event;
CREATE TABLE IF NOT EXISTS Event(
	EventID int not null auto_increment, 
    UserID int not null, 
    Event_Name varchar(30) not null, 
    Venue varchar(30) not null, 
    EventDate date, 
	EventTime time, 
    Price decimal(5,2) default 0,
    Stock int default 0,
    CancellationFee decimal(5,2) default 0,
    constraint Event_pk primary key (EventID),
    constraint Event_fk foreign key (UserID) references User(UserID)
);

insert into Event values (001, 001, "TaylorSwift Concert", "Stadium", "2024-03-09", "15:00:00", 300.00, 500,0);
insert into Event values (002, 001, "BlackPink Concert", "Stadium", "2023-03-09", "15:00:00", 300.00, 500, 0);

-- 3
create table TicketOfficer (
	UserID int not null, 
    EventID int not null, 
	constraint TicketOfficer_pk primary key (UserID), 
    constraint TickerOfficer_fk foreign key (EventID) references Event(EventID)
);
-- 4
Create table Customer (
	UserID int not null, 
    Email varchar(50),
    Balance decimal(6,2) default 1000.00,
    constraint Customer_pk primary key (UserID), 
    constraint Customer_fk foreign key (UserID) references User(UserID)
);

-- 5 
Create table Ticket(
	TicketID int not null auto_increment, 
    EventID int not null, 
    UserID int, 
    Type varchar(30), 
    Status varchar(30),
    constraint Ticket_pk primary key (TicketID), 
    constraint Ticket_fk1 foreign key (EventID) references Event(EventID),
    constraint Ticket_fk2 foreign key (UserID) references User(UserID)
);

-- 6
Create table Transaction (
	TransactionID int not null auto_increment, 
    EventID int not null, 
    UserID int not null, 
    NumTicketPurchased int not null, 
    Status varchar(30), 
    constraint Transaction_pk primary key (TransactionID),
    constraint Transaction_fk1 foreign key (EventID) references Event(EventID),
    constraint Transaction_fk2 foreign key (UserID) references User(UserID)
);
