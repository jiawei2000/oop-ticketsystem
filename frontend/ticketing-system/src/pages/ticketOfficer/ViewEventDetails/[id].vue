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
const ticketOfficers = ref([]);
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

const eventStatistics = reactive({
  "totalRevenue": "",
  "saleRevenue": "",
  "refundRevenue": "",
  "sales": "",
  "attendance": "",
  "noRefunds": "",
  "noCancelled": ""
})


onMounted(() => {
  console.log(route.params);
  eventId.value = route.params.id;
  console.log(eventId.value)
  getEvent();
  getTicketOfficer();
})

const getEvent = async () => {
  const getEventURL = "manager/getEventStatistics/" + eventId.value;
  axios.get(getEventURL)
    .then(response => {
      const data = response.data;
      console.log(data);
      //event 
      const eventData = data.event;
      event.eventId = eventData.eventId;
      event.eventname = eventData.eventName;
      event.status = eventData.status;
      event.date = formatDate(eventData.date);
      event.time = eventData.time;
      event.price = eventData.price;
      event.cancellationFee = eventData.cancellationFee;
      event.stock = eventData.stock;

      //eventStatistics 
      eventStatistics.totalRevenue = data.totalRevenue;
      eventStatistics.saleRevenue = data.saleRevenue;
      eventStatistics.refundRevenue = data.refundRevenue;
      eventStatistics.sales = data.sales;
      eventStatistics.attendance = data.attendance;
      eventStatistics.noRefunds = data.noRefunds;
      eventStatistics.noCancelled = data.noCancelled;

    })
    .catch(error => {
      console.log(error.message);
    })

};

const getTicketOfficer = async () => {
  const getTOURL = "manager/getTicketOfficers/" + eventId.value;
  axios.get(getTOURL)
    .then(response => {
      const data = response.data;
      console.log(data);
      ticketOfficers.value = data;

    })
    .catch(error => {
      console.log(error.message);
    })
}


const cancelEvent = async () => {
  console.log("A");
  const cancelEventURL = "manager/cancelEvent/" + eventId.value;
  axios.put(cancelEventURL)
  .then(response => {
      const data = response.data;
      console.log(data);
      //cancel successful
      message.value = "Event successfully cancelled";
      getEvent();
      isSuccessful.value = true; 
      OpenModal();
    })
    .catch(error => {
      console.log(error.message);
      //cancel unsuccessful 
      message.value = error.response.data;
      isSuccessful.value = false; 
      OpenModal();
    })
}

const editEventDetails = async () => {
  console.log("B");
}

const removeTicketOfficer = async (ticketOfficerId) => {
  console.log("C", ticketOfficerId);
  const removeTicketOfficerURL = "manager/removeTicketOfficers/" + ticketOfficerId;
  axios.delete(removeTicketOfficerURL)
    .then(response => {
      const data = response.data;
      console.log(data);
      message.value = data;
      getTicketOfficer(); //update table 
      isSuccessful.value = true;
      OpenModal(); //open message 
    })
    .catch(error => {
      console.log(error.message);
    })
}
//for message modal 
const isSuccessful = ref(false);
const message = ref('');
const MsgModal = ref(false);
const OpenModal = () => {
  MsgModal.value = true;
}
const CloseModal = () => {
  MsgModal.value = false;
}

const activeTab = ref(0);
</script>

<template>
  <!-- message modal -->
  <v-dialog v-model="MsgModal" max-width="500">
    <v-card>
      <v-card-title>
        <span class="headline">Message</span>
      </v-card-title>
      <v-card-text class="text-center">
        <p v-if=isSuccessful><v-icon class="success-icon">mdi-checkbox-marked-circle-outline</v-icon>{{ message }}</p>
        <p v-else><v-icon class="error-icon">mdi-alert-octagon-outline</v-icon>{{ message }}</p>
      </v-card-text>
      <v-card-actions class="justify-center">
        <v-btn color="primary" @click="CloseModal">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  <!-- TO creation modal -->

  <v-card>
    <!-- Display Event Details -->
    <v-tabs v-model="activeTab">
      <v-tab value="one">Event Details</v-tab>
      <v-tab value="two">Ticket Officers</v-tab>
    </v-tabs>

    <v-window v-model="activeTab">
      <!-- Tab1 -->
      <v-window-item value="one">
        <!-- Event details -->
        <v-card class="my-6 mx-6">
          <v-card-title>EventId: {{ event.eventId }}</v-card-title>
          <v-card-text>
            <!-- form (EventId, Eventname, status,  venue, date, time, price, cancellation fee, stock,  ) -->

            <v-form>
              <v-container>
                <!-- EventName and status -->
                <v-row>
                  <v-col cols=8>
                    <v-text-field v-model="event.eventname" label="Event Name" readonly></v-text-field>
                  </v-col>
                  <v-col cols=4>
                    <v-text-field v-model="event.status" label="Status" readonly></v-text-field>
                  </v-col>
                </v-row>
                <!-- Date and time -->
                <v-row>
                  <v-col cols=6>
                    <v-text-field v-model="event.date" label="Date" readonly></v-text-field>
                  </v-col>
                  <v-col cols=6>
                    <v-text-field v-model="event.time" label="Time" readonly></v-text-field>
                  </v-col>
                </v-row>
                <!-- Price, Cancellation Fee, Stock -->
                <v-row>
                  <v-col cols=4>
                    <v-text-field v-model="event.price" label="Price($)" readonly></v-text-field>
                  </v-col>
                  <v-col cols=4>
                    <v-text-field v-model="event.cancellationFee" label="Cancellation Fee($)" readonly></v-text-field>
                  </v-col>
                  <v-col cols=4>
                    <v-text-field v-model="event.stock" label="Stock" readonly></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-end">
            <v-btn variant="outlined" secondary="error" @click=editEventDetails>Edit Details</v-btn>
            <v-btn variant="outlined" color="error" @click=cancelEvent>Cancel</v-btn>
          </v-card-actions>
        </v-card>

        <!-- Event statistics -->
        <v-card class="my-6 mx-6">
          <v-card-title>Event Statistics</v-card-title>
          <v-card-text>
            <!-- form (EventId, Eventname, status,  venue, date, time, price, cancellation fee, stock,  ) -->
            <v-form>
              <v-container>
                <!-- Total sales -->
                <v-row>
                  <v-col>
                    <v-text-field v-model="eventStatistics.totalRevenue" label="Total Revenue" readonly></v-text-field>
                  </v-col>
                </v-row>
                <!-- sales rev, refund rev, sales -->
                <v-row>
                  <v-col cols=5>
                    <v-text-field v-model="eventStatistics.saleRevenue" label="Sales Revenue" readonly></v-text-field>
                  </v-col>
                  <v-col cols=5>
                    <v-text-field v-model="eventStatistics.refundRevenue" label="Refund Revenue"
                      readonly></v-text-field>
                  </v-col>
                  <v-col cols=2>
                    <v-text-field v-model="eventStatistics.sales" label="Sales" readonly></v-text-field>
                  </v-col>
                </v-row>
                <!-- Price, Cancellation Fee, Stock -->
                <v-row>
                  <v-col cols=4>
                    <v-text-field v-model="eventStatistics.attendance" label="Attendance" readonly></v-text-field>
                  </v-col>
                  <v-col cols=4>
                    <v-text-field v-model="eventStatistics.noRefunds" label="Number of refunds" readonly></v-text-field>
                  </v-col>
                  <v-col cols=4>
                    <v-text-field v-model="eventStatistics.noCancelled" label="Number of tickets to cancel"
                      readonly></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-form>
          </v-card-text>
        </v-card>
      </v-window-item>

      <!-- Tab2 -->
      <v-window-item value="two">
        <VCard>
          <v-card-title>Ticket Officers</v-card-title>
          <VCardText>
            <VDivider />
            <VTable class="text-no-wrap">
              <!-- TicketId, EventName, Username, Type, Status, Actions -->
              <thead>
                <tr>
                  <th>TicketOfficerId</th>
                  <th>Username</th>
                  <th>Password</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                <tr v-for="ticketOfficer in ticketOfficers" :key="ticketOfficer.userId">
                  <td>{{ ticketOfficer.userId }}</td>
                  <td>{{ ticketOfficer.userName }}</td>
                  <td>{{ ticketOfficer.password }}</td>
                  <td>
                    <v-btn variant="outlined" color="error"
                      @click="removeTicketOfficer(ticketOfficer.userId)">Remove</v-btn>
                  </td>
                </tr>
              </tbody>
            </VTable>
          </VCardText>
          <v-card-actions class="justify-end">
            <v-btn  @click=cancelEvent>Add Ticket Officer</v-btn>
          </v-card-actions>
        </VCard>
      </v-window-item>
    </v-window>
  </v-card>
</template>