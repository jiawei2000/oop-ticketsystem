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

            <!-- <tbody>
                <tr v-for="event in events" :key="event.eventId">
                    <td>{{ event.eventId }}</td>
                    <td>${{ event.totalRevenue }}</td>
                    <td>${{ event.saleRevenue }}</td>
                    <td>${{ event.refundRevenue }}</td>
                    <td>{{ event.sales }}</td>
                    <td>{{ event.attendance }}</td>
                    <td>{{ event.noRefunds }}</td>
                    <td>{{ event.noCancelled }}</td>
                </tr>
            </tbody> -->
        </VTable>
    </VCardText>
</VCard>
    </section>
</template>

<!-- <script setup>
import axios from "@axios";
import { onMounted, ref } from "vue";

const searchQuery = ref("");
const eventStatistics = ref([]);
const headers = [
    { text: 'Event ID', value: 'eventId' },
    { text: 'Total Revenue', value: 'totalRevenue' },
    { text: 'Sale Revenue', value: 'saleRevenue' },
    { text: 'Refund Revenue', value: 'refundRevenue' },
    { text: 'Sales', value: 'sales' },
    { text: 'Attendance', value: 'attendance' },
    { text: 'No Refunds', value: 'noRefunds' },
    { text: 'No Cancelled', value: 'noCancelled' },
];

const getEventStatistics = async () => {
    const displayEventStatisticsURL = "getEventStatistics/1"; // Assuming 1 is the managerId for testing
    try {
        const response = await axios.get(displayEventStatisticsURL);
        eventStatistics.value = [response.data];
    } catch (error) {
        console.error("Error fetching event statistics:", error.message);
    }
};

onMounted(() => {
    getEventStatistics();
});
</script> -->
