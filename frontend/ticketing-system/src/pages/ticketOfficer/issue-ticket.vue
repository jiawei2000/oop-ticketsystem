<script setup>
import { requiredValidator } from '@validators';
import axios from "@axios";
import { onMounted } from 'vue';
import { useRouter } from "vue-router";

const eventId = ref('');
const numberOfTickets = ref('');
const eventName = ref('');
const date = ref('');
const time = ref('');
const price = ref('');
const stock = ref('');
const cancellationFee = ref('');
const venue = ref('');

const isLoading = ref(false);
const refForm = ref();
const router = useRouter();

const getEventDetail = () => {
    const getEventDetailsURL = "events/" + eventId.value;
    axios.get(getEventDetailsURL)
        .then(response => {
            console.log("Response:", response.data);
            eventName.value = response.data.eventName;
            date.value = formatDate(response.data.date);
            time.value = response.data.time;
            price.value = response.data.price;
            stock.value = response.data.stock;
            cancellationFee.value = response.data.cancellationFee;
            venue.value = response.data.venue;
        })
        .catch(error => {
            console.log(error.message);
        });
}

const purchaseTicket = async () => {
    isLoading.value = true;
    const valid = refForm.value.validate();
    if (valid.valid == false) {
        isLoading.value = false;
        return;
    }

    const purchaseTicketURL = "ticketofficer/purchaseTicket";
    const data = {
        eventId: eventId.value,
        numTicketPurchased: numberOfTickets.value,
        userId: localStorage.getItem("UserId"),
        date: getCurrentDate()
    }
    console.log("Data: ", data);

    axios.post(purchaseTicketURL, data)
        .then(response => {
            console.log(response.data);
            //Redirect to ticket
            router.push({ name: 'ticketOfficer-ViewTicket', query: { eventId: eventId.value, transactionId: response.data.transaction.transactionId } });


            // window.location.href = `http://localhost:5173/ticketing-system/ticketofficer/ViewTicket?eventId=${eventId.value}&transactionId=${response.data.transactionId}`;

        })
        .catch(error => {
            console.log(error.message);
        });
    isLoading.value = false;

}
onMounted(() => {
    eventId.value = localStorage.getItem("EventId");
    getEventDetail();
});

const formatDate = (dateString) => {
    const [year, month, day] = dateString.split('-');
    return `${day}-${month}-${year}`;
}

const getCurrentDate = () => {
    const date = new Date();
    const year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }
    return `${year}-${month}-${day}`;
}
</script>

<template>
    <section>
        <VCard title="Issue Ticket">
            <VCardText>
                <VForm ref="refForm" @submit.prevent>
                    <VRow>
                        <VCol cols="2">
                            <VTextField v-model="eventId" label="Event ID" type="text" readonly />
                        </VCol>

                        <VCol cols="10">
                            <VTextField v-model="eventName" label="Event Name" type="text" readonly />
                        </VCol>

                        <VCol cols="4">
                            <VTextField v-model="price" label="Price" type="text" readonly />
                        </VCol>

                        <VCol cols="4">
                            <VTextField v-model="stock" label="Stock" type="text" readonly />
                        </VCol>

                        <VCol cols="4">
                            <VTextField v-model="cancellationFee" label="Cancellation Fee" type="text" readonly />
                        </VCol>

                        <VCol cols="6">
                            <VTextField v-model="date" label="Date" type="text" readonly />
                        </VCol>

                        <VCol cols="6">
                            <VTextField v-model="time" label="Time" type="text" readonly />
                        </VCol>

                        <VCol cols="12">
                            <VTextField v-model="numberOfTickets" label="Number of Tickets" type="number"
                                :rules="[requiredValidator]" />
                        </VCol>

                        <VCol cols="12" class="d-flex gap-4">
                            <VBtn type="submit" @click="purchaseTicket()">
                                <span v-if="!isLoading">Purchase</span>
                                <VProgressCircular v-if="isLoading" indeterminate color="success" :size="25" />
                            </VBtn>

                            <VBtn type="reset" color="secondary" variant="tonal">
                                Reset
                            </VBtn>

                        </VCol>
                    </VRow>
                </VForm>
            </VCardText>
        </VCard>
    </section>
</template>
