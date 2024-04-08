<script setup>
import axios from 'axios';

//https://vuejs.org/api/sfc-script-setup.html#basic-syntax
</script>


<template>
  <VTable height="250" fixed-header>
    <thead>
      <tr>
        <th class="text-uppercase">
          Events
        </th>
        <th>
          Venue
        </th>
        <th>
          Date
        </th>
        <th>
          Time
        </th>
        <th>
          Price
        </th>
        <th>
          Stock
        </th>
        <th>
          Cancellation Fee
        </th>
        <th>
          Actions
        </th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="event in events" :key="event.eventId">
        <td>
          {{ event.eventName }}
        </td>
        <td class="text-center">
          {{ event.venue }}
        </td>
        <td class="text-center">
          {{ formatDate(event.date) }}
        </td>
        <td class="text-center">
          {{ event.time }}
        </td>
        <td class="text-center">
          ${{ event.price }}
        </td>
        <td class="text-center">
          {{ event.stock }}
        </td>
        <td class="text-center">
          ${{ event.cancellationFee }}
        </td>
        <td>
          <button type="button" class="btn btn-success" @click="purchase(event.eventId)">Purchase</button>
        </td>

      </tr>
    </tbody>
  </VTable>
</template>

<script>
var displayEvent_URL = "http://localhost:8080/api/customer/displayEvents";
const events = await axios.get(displayEvent_URL)
  .then(response => {
    const data = response.data
    console.log(data);
    return data;
  })
  .catch(error => {
    console.log(error.message);
  });


export default {
  data() {
    return {

    };
  },
  methods: {
    formatDate(dateString) {
      const [year, month, day] = dateString.split('-');
      return `${day}-${month}-${year}`;
    },
    purchase(eventId){
      console.log(eventId);
    }
  }
};
</script>
