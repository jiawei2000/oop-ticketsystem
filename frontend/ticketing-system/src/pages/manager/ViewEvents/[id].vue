<template>
    <section>
      <!-- Event Report Page -->
      <VCard v-if="eventStatistics">
        <VCardText>
          <VDivider />
  
          <VTable class="text-no-wrap">
            <thead>
              <tr>
                <th>Event ID</th>
                <th>Event Name</th>
                <th>Total Revenue</th>
                <th>Sale Revenue</th>
                <th>Refund Revenue</th>
                <th>Sales</th>
                <th>Attendance</th>
                <th>Num Refunds</th>
                <th>Num Cancelled</th>
              </tr>
            </thead>
  
            <tbody>
              <tr v-for="eventStat in eventStatistics" :key="eventStat.event.eventId">
                <td>{{ eventStat.event.eventId }}</td>
                <td>{{ eventStat.event.eventName }}</td>
                <td>${{ eventStat.totalRevenue }}</td>
                <td>${{ eventStat.saleRevenue }}</td>
                <td>${{ eventStat.refundRevenue }}</td>
                <td>{{ eventStat.sales }}</td>
                <td>{{ eventStat.attendance }}</td>
                <td>{{ eventStat.noRefunds }}</td>
                <td>{{ eventStat.noCancelled }}</td>
              </tr>
            </tbody>
          </VTable>
        </VCardText>
      </VCard>
    </section>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        eventStatistics: []
      };
    },
    methods: {
      async fetchEventStatistics() {
        try {
          const eventIds = [1, 2, 3]; // Replace with your list of event IDs or fetch them dynamically
          const requests = eventIds.map(async (eventId) => {
            const reportURL = `http://localhost:8080/api/manager/getEventStatistics/${eventId}`;
            const response = await axios.get(reportURL);
            return response.data;
          });
          this.eventStatistics = await Promise.all(requests);
        } catch (error) {
          console.error("Error fetching event statistics:", error);
        }
      }
    },
    mounted() {
      this.fetchEventStatistics();
    }
  };
  </script>