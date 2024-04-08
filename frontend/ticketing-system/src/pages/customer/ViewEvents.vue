<script setup>
import axios from "@axios";
import { onMounted } from "vue";
import { useRouter } from "vue-router";
import { ref } from "vue";

const router = useRouter();

const events = ref([]);
const searchQuery = ref("");

const getEvents = async () => {
    const displayEvent_URL = "events";
    axios.get(displayEvent_URL)
        .then(response => {
            const data = response.data
            events.value = data;
        })
        .catch(error => {
            console.log(error.message);
        });
}

const formatDate = (dateString) => {
    const [year, month, day] = dateString.split('-');
    return `${day}-${month}-${year}`;
}

const viewDetails = (eventId) => {
    router.push({ path: 'ViewEventDetails/' + eventId });
}

onMounted(() => {
    getEvents();
});

</script>

<template>
    <section>

        <VCard>
            <VCardText>
                <div class="mb-3">
                    <VTextField v-model="searchQuery" placeholder="Search Event" />
                </div>
                <VDivider />

                <VTable class="text-no-wrap">
                    <thead>
                        <tr>
                            <th>Event Name</th>
                            <th>Venue</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Cancellation Fee</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr v-for="event in events" :key="event.eventId">
                            <td>{{ event.eventName }}</td>
                            <td>{{ event.venue }}</td>
                            <td>{{ formatDate(event.date) }}</td>
                            <td>{{ event.time }}</td>
                            <td>${{ event.price }}</td>
                            <td>{{ event.stock }}</td>
                            <td>${{ event.cancellationFee }}</td>
                            <td>
                                <v-btn variant="outlined" @click="viewDetails(event.eventId)">View Details</v-btn>
                            </td>
                        </tr>
                    </tbody>
                </VTable>
            </VCardText>
        </VCard>
    </section>
</template>