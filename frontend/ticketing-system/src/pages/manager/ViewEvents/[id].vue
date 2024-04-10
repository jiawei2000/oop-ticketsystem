<template>
    <section>
        <!-- Event statistics table -->
        <VCard>
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
            <tr v-if="eventStatistics" :key="eventStatistics.eventId">
              <td>{{ eventStatistics.eventId }}</td>
              <td>{{ eventStatistics.eventName }}</td>
              <td>${{ eventStatistics.totalRevenue }}</td>
              <td>${{ eventStatistics.saleRevenue }}</td>
              <td>${{ eventStatistics.refundRevenue }}</td>
              <td>{{ eventStatistics.sales }}</td>
              <td>{{ eventStatistics.attendance }}</td>
              <td>{{ eventStatistics.noRefunds }}</td>
              <td>{{ eventStatistics.noCancelled }}</td>
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
      eventStatistics: null
    };
  },
  methods: {
    async fetchEventStatistics(currEventId) {
  try {
    const reportURL = "http://localhost:8080/api/manager/getEventStatistics/" + currEventId;
    const response = await axios.get(reportURL);
    this.eventStatistics = response.data;
  } catch (error) {
    console.error("Error fetching event statistics:", error);
  }
}
  },
  mounted() {
    const currEventId = 1; // Replace with the actual current event ID
    this.fetchEventStatistics(currEventId);
  }
};
</script>