<script setup>
import axios from "@axios";
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const searchQuery = ref("");
const events = ref([]);
//Event script
const getEvents = async () => {
    const displayEventURL = "events";
    axios.get(displayEventURL)
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
onMounted(() => {
    getEvents();
});

// Modal related script
//What is needed for purchase? event ID, customer ID, Quantity
const modalOpen = ref(false);
const formData = reactive({
    eventId: 0,
    eventName: "",
    venue: "",
    date: "",
    time: "",
    price: 0,
    stock: 0,
    cancellationFee: 0,
    quantity: 0
});
// const eventId = 0;
// const eventName = "A";
// const venue = "B";
// const date = "C";
// const time = "D";
// const price = 0;
// const stock = 0;
// const cancellationFee = 0;


const openModal = () => {
    modalOpen.value = true;
}

const closeModal = () => {
    modalOpen.value = false;
}

const viewDetails = (currEventId) => {
    // router.push({ path: 'ViewEventDetails/' + eventId });
    console.log(currEventId);
    console.log(formData.value);
    console.log(events.value);
    for (const event of events.value) {
        if (event.eventId == currEventId) {
            formData.eventId = event.eventId;
            formData.eventName = event.eventName;
            formData.venue = event.venue;
            formData.date = formatDate(event.date);
            formData.time = event.time;
            formData.price = event.price;
            formData.stock = event.stock;
            formData.cancellationFee = event.cancellationFee;
            break;
        }
    }
    openModal();
}

const makePurchase = async () => {
    // Submit form logic here
    console.log('hi');
    console.log('Form submitted:', formData.value);
    closeModal(); // Close the modal after form submission
}

const validateQuantity = (value) => {
    // Check if the value is numeric
    if (value >= 1 && value <= 5) {
        return true; // Valid numeric input
    } else {
        return 'Quantity must be within purhcase limites of 1 to 5'; // Error message for invalid input
    }
}
</script>

<template>
    <section>
        <!-- Modal component -->
        <v-dialog v-model="modalOpen" max-width="50%">
            <v-card>
                <v-card-title>
                    <span class="headline">Event Description</span>
                </v-card-title>
                <v-card-text>
                    <!-- Form fields -->
                    <v-form @submit.prevent="makePurchase">
                        <v-container>
                            <!-- event name -->
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field v-model="formData.eventName" label="Event Name" readonly></v-text-field>
                                </v-col>
                            </v-row>
                            <!-- venue -->
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field v-model="formData.venue" label="Venue" readonly></v-text-field>
                                </v-col>
                            </v-row>
                            <!-- Date and time -->
                            <v-row>
                                <v-col cols="6">
                                    <v-text-field v-model="formData.date" label="Date" readonly></v-text-field>
                                </v-col>
                                <v-col cols="6">
                                    <v-text-field v-model="formData.time" label="Time" readonly></v-text-field>
                                </v-col>
                            </v-row>
                            <!-- Price, Stock, and cancellation Fee -->
                            <v-row>
                                <v-col cols="4">
                                    <v-text-field v-model="formData.price" label="Price($)" readonly></v-text-field>
                                </v-col>
                                <v-col cols="4">
                                    <v-text-field v-model="formData.cancellationFee" label="Cancellation Fee($)"
                                        readonly></v-text-field>
                                </v-col>
                                <v-col cols="4">
                                    <v-text-field v-model="formData.stock" label="Stock" readonly></v-text-field>
                                </v-col>
                            </v-row>
                            <!-- Quantity -->
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field v-model="formData.quantity" label="Specify Quantity" type=number min=0
                                        max=5 :rules="[validateQuantity]"></v-text-field>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-btn color="primary" @click="makePurchase">Purchase</v-btn>
                    <v-btn @click="closeModal">Close</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <!-- Event table -->
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