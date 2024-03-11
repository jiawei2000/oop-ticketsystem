
-- Database: OOP_Project 
CREATE DATABASE IF NOT EXISTS OOP_Project;
use OOP_Project; 
 
DROP TABLE IF EXISTS `USER`;
CREATE TABLE IF NOT EXISTS `USER`(
	UserID int not null auto_increment,
    username varchar(30) not null, 
    password varchar(30) not null, 
    user_type char(1) not null, -- either 'C', 'M', 'T'
	constraint USER_pk primary key (UserID)
);
insert into USER values (001, 'userTest', 'password123', 'M');


DROP TABLE IF EXISTS `EVENT`;
CREATE TABLE IF NOT EXISTS `EVENT`(
	EventID int not null auto_increment, 
    UserID int not null, 
    Event_Name varchar(30) not null, 
    Venue varchar(30) not null, 
    Event_Date date, 
	Event_Time time, 
    Price decimal(5,2), 
    Stock int, 
    constraint EVENT_pk primary key (EventID, UserID),
    constraint EVENT_fk foreign key (UserID) references USER(UserID)
);

insert into EVENT values (001, 001, "TaylorSwift Concert", "Stadium", "2024-03-09", "15:00:00", 300.00, 500);
insert into EVENT values (002, 001, "BlackPink Concert", "Stadium", "2023-03-09", "15:00:00", 300.00, 500);
