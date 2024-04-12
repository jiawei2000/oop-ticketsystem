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
          <v-btn color="green" @click="generateCSV">Generate Report</v-btn>
        </VCardText>
      </VCard>
    </section>
  </template>
  
  <script>
  import axios from 'axios';
  import Papa from 'papaparse'; // Import Papa as default
  
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
      },
      generateCSV() {
        const columns = [
          "event_ID",
          "manager_ID",
          "event_name",
          "venue",
          "date",
          "total_revenue",
          "sale_revenue",
          "refund_revenue",
          "sales",
          "attendance",
          "number_of_refunds",
          "number_of_cancelled",
        ];
  
        const csvData = [columns];
  
        this.eventStatistics.forEach(eventStat => {
          csvData.push([
            eventStat.event.eventId,
            eventStat.event.managerId,
            eventStat.event.eventName,
            eventStat.event.venue,
            eventStat.event.date.padEnd(100),
            eventStat.totalRevenue,
            eventStat.saleRevenue,
            eventStat.refundRevenue,
            eventStat.sales,
            eventStat.attendance,
            eventStat.noRefunds,
            eventStat.noCancelled
          ]);
        });
  
        // Convert data to CSV format using papaparse unparse function
        const csvString = Papa.unparse(csvData);
  
        // Save CSV data to a file
        this.saveCSV(csvString, "all_events_report");
      },
      saveCSV(csvData, filename) {
        const blob = new Blob([csvData], { type: 'text/csv;charset=utf-8;' });
        if (navigator.msSaveBlob) { // IE 10+
          navigator.msSaveBlob(blob, filename + '.csv');
        } else {
          const link = document.createElement("a");
          if (link.download !== undefined) {
            const url = URL.createObjectURL(blob);
            link.setAttribute("href", url);
            link.setAttribute("download", filename + '.csv');
            link.style.visibility = 'hidden';
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
          }
        }
      }
    },
    mounted() {
      this.fetchEventStatistics();
    }
  };
  </script>
  