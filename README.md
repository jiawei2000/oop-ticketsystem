# IS442 OOP Ticketing Project, TicketTime

## Description
Introducing TicketTime, a versatile ticket booking application. It enables customers to effortlessly view and purchase event tickets, empowers managers to efficiently create and manage events, and enables ticket officers to seamlessly verify and process on-site ticket sales.Instructions to run our application are included below.  

## Database configuration
By default the application is linked to our MySQL server hosted on AWS. However the configuration can be easily changed to a local SQL instance by modifying the datasource in this file.
<pre>
ticketing-system/src/main/resources/application.properties
</pre>

## Installation
### Frontend
Run the following commands from oop-ticketsystem folder
<pre>
cd frontend/ticketing-system <br>
npm install
</pre>

### Backend
Run the following commands from oop-ticketsystem folder
<pre>
cd ticketing-system
./compile.bat
</pre>

## Usage 
### Step 1: Start up Java Spring Boot (Backend)
Using a terminal run the following commands
<pre>
cd ticketing-system 
./run.bat
</pre>
This command runs the maven script that automatically compile our source codes into target/class folder.<br>
The server will be hosted on `http://localhost:8080/`

### Step 2: Start up Vue.Js (Frontend)
Using a different terminal, run the following commands
<pre>
cd frontend/ticketing-system <br>
npm run dev 
</pre>

This command starts a development server that serves our web application locally.<br>
The application will be hosted on `http://localhost:5153/`

#### Alternatively
Build the application with the following command
<pre>
npm run build 
</pre>
Move the compiled codes in the `dist` folder to the web server of your choice.

### Step 3: Access the application from the browser
Use your browser of choice to access the application at the link `http://localhost:5153/login`

## Pre Created Accounts
The following accounts have been created to help faciliate testing
### Managers
<pre>
Username: ManagerA
Password: P@ssw0rd
</pre>
<pre>
Username: ManagerB
Password: P@ssw0rd
</pre>

### Customers
Do register your own account with a valid email if you would like to receive the emails.
<pre>
Username: jiawei
Password: password
</pre>

### Ticket Officer
Ticket officers for the `Night in the Museum` event
<pre>
Username: Nightofficer1
Password: password
</pre>
<pre>
Username: Nightofficer2
Password: password
</pre>



