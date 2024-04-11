<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from "vue-router";
import axios from "@axios";

const router = useRouter();
const eventId = ref(null);
const route = useRoute();

const formatDate = (dateString) => {
    const [year, month, day] = dateString.split('-');
    return `${day}-${month}-${year}`;
}

const event = reactive({
  "eventId": "", 
  "eventname": "", 
  "status": "",
  "date": "", 
  "time": "",
  "price": "", 
  "cancellationFee": "",
  "stock": "" 
});

onMounted(() => {
  console.log(route.params);
  eventId.value = route.params.id;
  console.log(eventId.value)
  getEvent();
})

const getEvent = async () => {
  const getEventURL = "events/" + eventId.value;
  axios.get(getEventURL)
    .then(response => {
      const data = response.data;
      console.log(data);
      event.eventId = data.eventId;
      event.eventname = data.eventName;
      event.status = data.status; 
      event.date = formatDate(data.date);
      event.time = data.time; 
      event.price = data.price; 
      event.cancellationFee = data.cancellationFee;
      event.stock = data.stock;
      console.log(event)
    })
    .catch(error => {
      console.log(error.message);
    })

};


</script>

<template>
  <section>
    <!-- Display Event Details -->
    <v-card>
      <v-card-title>Event Details of EventId: {{ event.eventId }}</v-card-title>
      <v-card-text>
        <!-- form (EventId, Eventname, status,  venue, date, time, price, cancellation fee, stock,  ) -->

       <v-form> 
        <v-container>
          <!-- EventName and status -->
          <v-row>
            <v-col cols = 8> 
              <v-text-field v-model="event.eventname" label="Event Name" readonly></v-text-field>
            </v-col>
            <v-col cols = 4> 
              <v-text-field v-model="event.status" label="Status" readonly></v-text-field>
            </v-col>
          </v-row>
          <!-- Date and time -->
          <v-row>
            <v-col cols = 6>
              <v-text-field v-model="event.date" label="Date" readonly></v-text-field>
            </v-col>
            <v-col cols = 6>
              <v-text-field v-model="event.time" label="Time" readonly></v-text-field>
            </v-col>
          </v-row>
          <!-- Price, Cancellation Fee, Stock -->
          <v-row>
            <v-col cols = 4>
              <v-text-field v-model="event.price" label="Price($)" readonly></v-text-field>
            </v-col>
            <v-col cols = 4>
              <v-text-field v-model="event.cancellationFee" label="Cancellation Fee($)" readonly></v-text-field>
            </v-col>
            <v-col cols = 4>
              <v-text-field v-model="event.stock" label="Stock" readonly></v-text-field>
            </v-col>
          </v-row>
        </v-container>
       </v-form>
      </v-card-text>
      <v-card-actions class="justify-end">
        <v-btn variant="outlined" secondary="error">Edit Details</v-btn>
        <v-btn variant="outlined" color="error">Cancel</v-btn>
      </v-card-actions>
    </v-card>
  </section>
</template>