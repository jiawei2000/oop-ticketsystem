<script setup>
import axios from "@axios";
import { onMounted } from "vue";
import { useRouter } from "vue-router";


const router = useRouter();
const searchQuery = ref("");
const events = ref([]);
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
    status: "",
    totalRevenue: 0,
    saleRevenue: 0,
    refundRevenue: 0,
    sales: 0,
    attendance: 0,
    noRefunds: 0,
    noCancelled: 0
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

const viewStats = async (currEventId) => {
    // console.log(currEventId);
    // console.log(formData.value);
    // console.log(events.value);
    const statsURL = "manager/getEventStatistics/" + currEventId;
    await axios.get(statsURL)
        .then(async response => {
            console.log("Event: ", response.data);
            formData.eventId = response.data.event.eventId;
            formData.eventName = response.data.event.eventName;
            formData.status = response.data.event.status;
            formData.totalRevenue = response.data.totalRevenue;
            formData.saleRevenue = response.data.saleRevenue;
            formData.refundRevenue = response.data.refundRevenue;
            formData.sales = response.data.sales;
            formData.attendance = response.data.attendance;
            formData.noRefunds = response.data.noRefunds;
            formData.noCancelled = response.data.noCancelled;
        })
        .catch(error => {
            console.log(error);
        });
    openModal();
}

const editEvent = (currEventId) => {
    router.push({ path: 'viewevents/' + currEventId });
    // WIP, eventually it should redirect to the editing page

}
const cancelMessage = ref('');
const isSuccessful = ref(true);
const cancelEvent = async (currEventId) => {
    if (await openCancel()) {
        const cancelURL = "manager/cancelEvent/" + currEventId;
        await axios.put(cancelURL)
            .then(async response => {
                // console.log(response.data);
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

const generateReport = async () => {
    const reportURL = "manager/getEventStatistics";
    await axios.get(reportURL)
        .then(async response => {
            // console.log(response.data);
            var rows = [["event_ID", "manager_ID", "event_name",
                "venue", "date", "total_revenue",
                "sales_revenue", "refund_revenue", "sales",
                "attendance", "refunds", "cancellations"]];
            for (const eventData of response.data) {
                rows.push(
                    [eventData.event.eventId, eventData.event.managerId, eventData.event.eventName,
                    eventData.event.venue, eventData.event.date, eventData.totalRevenue,
                    eventData.saleRevenue, eventData.refundRevenue, eventData.sales,
                    eventData.attendance, eventData.noRefunds, eventData.noCancelled]
                );
            }
            let csvContent = "data:text/csv;charset=utf-8," + rows.map(e => e.join(",")).join("\n");
            var encodedUri = encodeURI(csvContent);
            var link = document.createElement("a");
            link.setAttribute("href", encodedUri);
            link.setAttribute("download", "event_statistics_report.csv");
            document.body.appendChild(link);
            link.click();
            console.log("Report Generated Successfully");
            document.body.removeChild(link);
        })
        .catch(error => {
            console.log(error);
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
                            <!-- Event ID -->
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field v-model="formData.eventId" label="Event ID" readonly></v-text-field>
                                </v-col>
                            </v-row>
                            <!-- Event Name -->
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field v-model="formData.eventName" label="Event Name"
                                        readonly></v-text-field>
                                </v-col>
                            </v-row>
                            <!-- Event Status -->
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field v-model="formData.status" label="Event Status"
                                        readonly></v-text-field>
                                </v-col>
                            </v-row>
                            <!-- Revenue -->
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field v-model="formData.totalRevenue" label="Total Revenue ($)"
                                        readonly></v-text-field>
                                </v-col>
                            </v-row>
                            <v-row>
                                <v-col cols="6">
                                    <v-text-field v-model="formData.saleRevenue" label="Sales Revenue ($)"
                                        readonly></v-text-field>
                                </v-col>
                                <v-col cols="6">
                                    <v-text-field v-model="formData.refundRevenue" label="Refunds Revenue ($)"
                                        readonly></v-text-field>
                                </v-col>
                            </v-row>
                            <!-- Sales -->
                            <v-row>
                                <v-col cols="12">
                                    <v-text-field v-model="formData.sales" label="Sales" readonly></v-text-field>
                                </v-col>
                            </v-row>
                            <!-- Attendance, Refunds and Cancellations -->
                            <v-row>
                                <v-col cols="4" sm="12">
                                    <v-text-field v-model="formData.attendance" label="Attendance"
                                        readonly></v-text-field>
                                </v-col>
                                <v-col cols="4" sm="12">
                                    <v-text-field v-model="formData.noRefunds" label="Refunds" readonly></v-text-field>
                                </v-col>
                                <v-col cols="4" sm="12">
                                    <v-text-field v-model="formData.noCancelled" label="Cancellations"
                                        readonly></v-text-field>
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
                    <p v-if=isSuccessful><v-icon class="success-icon">mdi-checkbox-marked-circle-outline</v-icon>{{
            cancelMessage }}</p>
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
                <div class="mb-3 d-flex align-center flex-wrap gap-6">
                    <VTextField v-model="searchQuery" placeholder="Search Event" />
                    <v-btn color="primary" variant="elevated" @click="generateReport">Generate Report</v-btn>
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
                            <th>Status</th>
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
                            <td>{{ event.status }}</td>
                            <td>
                                <!-- <v-btn variant="outlined" @click="viewStats(event.eventId)">View Statistics</v-btn> -->
                                <IconBtn @click="viewStats(event.eventId)">
                                    <VIcon icon=" bx-show" />
                                </IconBtn>

                                <IconBtn @click="editEvent(event.eventId)">
                                    <VIcon icon=" bx-edit" />
                                </IconBtn>

                                <IconBtn v-if="event.status === 'Active'" @click="cancelEvent(event.eventId)">
                                    <VIcon icon=" bx-trash" />
                                </IconBtn>

                                <!-- <v-btn color="secondary" variant="outlined" @click="editEvent(event.eventId)">Edit
                                    Details</v-btn> -->

                                <!-- <p v-if="event.status === 'Active'"> <v-btn color="error" variant="outlined"
                                        @click="cancelEvent(event.eventId)">Cancel Event</v-btn></p>
                                <p v-else> <v-btn color="error" variant="outlined" :disabled="true">Cancel Event</v-btn>
                                </p> -->
                            </td>
                        </tr>
                    </tbody>
                </VTable>
            </VCardText>
        </VCard>
    </section>
</template>
