<template>
    <v-card
  class="ma-5"
  max-width="95%"
  color="white"
>

  <!-- <v-card-text> -->
    <div class="card-container">
      <div class="card">
        <h2>Create New Event Listing</h2>
        <form class="event-listing-form">
          <div class="form-group">
            <label>Event Name:</label>
            <v-textarea
            row-height="15"
            rows="1"
            variant="outlined"
        ></v-textarea>
          </div>
          <div class="form-group">
            <label>Venue</label>
            <v-textarea
            row-height="15"
            rows="1"
            variant="outlined"
        ></v-textarea>
          </div>
          <div class="form-group">
            <label>Ticket Price</label>
            <v-textarea
            row-height="15"
            rows="1"
            variant="outlined"
        ></v-textarea>
          </div>
          <div class="form-group">
            <label>Number of Tickets Available</label>
            <v-textarea
            row-height="15"
            rows="1"
            variant="outlined"
        ></v-textarea>
           </div>
        <div class="form-group">
          <label for="closingDate">Closing Date:</label>
          <input type="date" id="closingDate" required />
        </div>
          <button type="submit" class="btn btn-primary">Create Event Listing</button>
        </form>
      </div>
    </div>
  <!-- </v-card-text> -->
</v-card>
  </template>
  
  

<style>

    h2{
    margin-top: 10px;
    margin-bottom: 15px;
    text-align:center;
    }

    .event-listing-form {
    max-width: 800px; 
    margin: 0 auto; /* Center the form horizontally */
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;

    }

    .form-group {
    margin-bottom: 15px;
    margin-left: 10px;

    }

    label {
    display: block;
    font-weight: bold;
    }

    input[type="text"] {
    width: 40%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    }
    select {
    width: 40%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    }

    input[type="date"]{
    width: 40%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    }
    textarea {
    width: 40%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    height:auto; 
    resize: none; /* Allow vertical resizing */
    overflow-y: hidden; /* Hide vertical scrollbar */
    }

    .btn {
    background-color: #007bff;
    color: #fff;
    border: none;
    padding: 10px 20px;
    border-radius: 4px;
    cursor: pointer;
    margin-left: 10px;
    }

    .btn-primary {
    background-color: #007bff; 
    }

    .btn-primary:hover {
    background-color: #0056b3; 
    /* darker when you hover over it */
    }

    .error{
    color: #0056b3; 
    }
</style>