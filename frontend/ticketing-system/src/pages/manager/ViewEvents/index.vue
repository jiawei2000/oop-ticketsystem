<script setup>
import axios from "@axios";
import { onMounted } from "vue";
import { useRouter } from "vue-router";


const router = useRouter();
const searchQuery = ref("");
const events = ref([]);
const managerId = 1; // This is hardcoded for testing purposes, it will need to be changed dynamically
//Event script
const getEvents = async () => {
    const displayEventURL = "events"
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

const formData = reactive({
    eventId: 0,
    eventName: "",
    venue: "",
    date: "",
    time: "",
    price: 0,
    stock: 0,
    cancellationFee: 0
});

//for View Event Details modal
const modalOpen = ref(false);
const openModal = () => {
    modalOpen.value = true;
}
const closeModal = () => {
    modalOpen.value = false;
}

//for cancelConfirm modal
const cancelConfirmModalOpen = ref(false);
const openCancel = () => {
    cancelConfirmModalOpen.value = true;
    return new Promise((resolve, reject) => {
        cancelConfirmModalOpen.resolve = resolve;
        cancelConfirmModalOpen.reject = reject;
    });
}
const confirm = () => {
    cancelConfirmModalOpen.resolve(true);
    cancelConfirmModalOpen.value = false;
}
const closeCancel = () => {
    cancelConfirmModalOpen.resolve(false);
    cancelConfirmModalOpen.value = false;
}

//for cancelMessage modal
const cancelModalOpen = ref(false);
const cancelOpenModal = () => {
    cancelModalOpen.value = true;
}
const cancelCloseModal = () => {
    cancelModalOpen.value = false;
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

const editEvent = (currEventId) => {
 // router.push({ path: 'EditEventDetails/' + currEventId });
 // WIP, eventually it should redirect to the editing page

}
const cancelMessage = ref('');
const isSuccessful = ref(true);
const cancelEvent = async (currEventId) => {
    if (await openCancel())
    {
    const cancelURL = "manager/cancelEvent/" + currEventId;
    await axios.put(cancelURL)
            .then(async response => {
                console.log(response.data);
                cancelMessage.value = "Event Successfully Cancelled";
                closeModal();
                isSuccessful.value = true;
                cancelOpenModal();
            })
            .catch(error => {
                console.log(error.response.data);
                cancelMessage.value = error.response.data;
                closeModal();
                isSuccessful.value = false;
                cancelOpenModal();
            });
    }
}

const generateReports = async (currEventId) => {
    const reportURL = "manager/getEventStatistics";
    await axios.get(reportURL)
            .then(async response => {
                console.log(response.data);
                const fs = require("fs");
                const { stringify } = require("csv-stringify");
                for (const eventData of response.data) {
                    const filename = eventData.event.eventName + "_event_report.csv";
                    const writableStream = fs.createWriteStream(filename);

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

                    const stringifier = stringify({ header: true, columns: columns });
                    stringifier.write(response.data.event.eventId);
                    stringifier.write(response.data.event.managerId);
                    stringifier.write(response.data.event.eventName);
                    stringifier.write(response.data.event.venue);
                    stringifier.write(response.data.event.date);
                    stringifier.write(response.data.totalRevenue);
                    stringifier.write(response.data.saleRevenue);
                    stringifier.write(response.data.refundRevenue);
                    stringifier.write(response.data.sales);
                    stringifier.write(response.data.attendance);
                    stringifier.write(response.data.noRefunds);
                    stringifier.write(response.data.noCancelled);
                    stringifier.pipe(writableStream);
                    console.log("Report Generated Successfully");
                }
                closeModal();
            })
            .catch(error => {
                console.log(error.response.data);
                closeModal();
            });
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
                    <v-form @submit.prevent="cancelEvent">
                        <v-container>
                            <!-- event name -->
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field v-model="formData.eventName" label="Event Name"
                                        readonly></v-text-field>
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
                                <v-col cols="4" sm="12">
                                    <v-text-field v-model="formData.price" label="Price($)" readonly></v-text-field>
                                </v-col>
                                <v-col cols="4" sm="12">
                                    <v-text-field v-model="formData.cancellationFee" label="Cancellation Fee($)"
                                        readonly></v-text-field>
                                </v-col>
                                <v-col cols="4" sm="12">
                                    <v-text-field v-model="formData.stock" label="Stock" readonly></v-text-field>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-form>
                </v-card-text>
                <v-card-actions class="justify-end">
                    <v-btn @click="closeModal">Close</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

<!-- Cancellation confirmation modal -->
        <v-dialog v-model="cancelConfirmModalOpen" max-width="500" @keydown.esc="closeCancel">
            <v-card>
                <v-card-title>
                    <span class="headline">Cancellation Confirmation</span>
                </v-card-title>
                <v-card-text class="text-center">
                    <v-icon class="error-icon">mdi-alert-octagon-outline</v-icon>
                    Warning: Are you sure you want to cancel this event? This action cannot be undone!
                </v-card-text>
                <v-card-actions class="justify-center">
                    <v-btn color="error" @click="confirm">Confirm</v-btn>
                    <v-btn color="primary" @click="closeCancel">Close</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

<!-- Cancellation message modal -->
        <v-dialog v-model="cancelModalOpen" max-width="500">
            <v-card>
                <v-card-title>
                    <span class="headline">Cancellation Message</span>
                </v-card-title>
                <v-card-text class="text-center">
                    <p v-if = isSuccessful><v-icon class="success-icon">mdi-checkbox-marked-circle-outline</v-icon>{{ cancelMessage }}</p>
                    <p v-else><v-icon class="error-icon">mdi-alert-octagon-outline</v-icon>{{ cancelMessage }}</p>
                </v-card-text>
                <v-card-actions class="justify-center">
                    <v-btn color="primary" @click="cancelCloseModal">Close</v-btn>
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
                                <v-btn color="secondary" variant="outlined" @click="editEvent(event.eventId)">Edit Details</v-btn>
                                <p v-if="event.status === 'Active'"> <v-btn color="error" variant="outlined" @click="cancelEvent(event.eventId)">Cancel Event</v-btn></p>
                                <p v-else> <v-btn color="error" variant="outlined" disabled=true>Cancel Event</v-btn></p>
                            </td>
                        </tr>
                    </tbody>
                </VTable>
            </VCardText>
        </VCard>
    </section>
</template>

