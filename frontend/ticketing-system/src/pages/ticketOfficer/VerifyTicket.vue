<script setup>
import axios from "@axios";
import { ref } from 'vue';
import { useRouter } from "vue-router";
const router = useRouter();

const tickets = ref([]);
const tableVisible = ref(false);

const isVisible = () => {
    if(tickets.value.length === 0){
        tableVisible.value = false;
    }
    else{
        tableVisible.value = true;
    }
}


//successful message 
const isSuccessful = ref(false);

// message v-dialog 
const MsgModal = ref(false);
const OpenModal = () => {
    MsgModal.value = true;
}
const CloseModal = () => {
    MsgModal.value = false;
}


//form submission of serialcode 
const code = ref('');
const message = ref('');
const submitForm = async () => {

    let send_transaction_data = {
        "eventId": 1, //ticket officer eventId
        "serial": code.value
    }
    code.value = ""; //clear form input

    const verifyTicketURL = "ticket/verify";
    await axios.post(verifyTicketURL, send_transaction_data)
        .then(async response => {
            console.log(response.data);
            const data = response.data
            if (data.status !== "Active") {
                message.value = "Ticket status is not 'Active'.";
                isSuccessful.value = false;
                OpenModal();
            }
            else {
                let ticket = {
                    "ticketId": data.ticketId,
                    "eventname": data.eventname,
                    "username": data.username,
                    "type": data.type,
                    "status": data.status
                };
                const foundTicket = tickets.value.find(ticket => ticket.ticketId === data.ticketId);
                if (foundTicket) {
                    console.log(foundTicket);
                    message.value = "Ticket exists in table below.";
                    isSuccessful.value = false;
                    OpenModal();
                }
                else {
                    tickets.value.push(ticket);
                    isVisible();
                }
            }
        })
        .catch(error => {
            console.log(error.response.data);
            message.value = error.response.data;
            isSuccessful.value = false;
            OpenModal();
        });

};


const changeTicketStatus = async (ticketId) => {
    console.log(ticketId);
    //change ticket status and remove ticket from tickets list 
    const verifyTicketURL = "ticket/updateTicketStatusToUsed/" + ticketId;
    axios.put(verifyTicketURL)
        .then(async response => {
            console.log(response.data);
            //removal 
            const index = tickets.value.findIndex(ticket => ticket.ticketId === ticketId);
            if (index >= 0) {
                tickets.value.splice(index, 1);
            }
            isVisible();
            //open successful message 
            message.value = "Ticket successfully verified!"
            isSuccessful.value = true;
            OpenModal();
        })
        .catch(error => {
            console.log(error.data);
        });


}
</script>


<template>
    <section>
        <!-- Ticket code -->
        <v-card>
            <v-card-title>Enter Ticket Code</v-card-title>
            <v-card-text>
                <v-form @submit.prevent="submitForm">
                    <v-text-field v-model="code" label="Ticket Code"></v-text-field>
                </v-form>
            </v-card-text>
            <v-card-actions class="justify-end">
                <v-btn color="primary" @click="submitForm">Submit</v-btn>
            </v-card-actions>
        </v-card>

        <!-- Error message modal -->
        <v-dialog v-model="MsgModal" max-width="500">
            <v-card>
                <v-card-title>
                    <span class="headline">Message</span>
                </v-card-title>
                <v-card-text class="text-center">
                    <p v-if=isSuccessful><v-icon class="success-icon">mdi-checkbox-marked-circle-outline</v-icon>{{
                        message }}</p>
                    <p v-else><v-icon class="error-icon">mdi-alert-octagon-outline</v-icon>{{ message }}</p>
                </v-card-text>
                <v-card-actions class="justify-center">
                    <v-btn color="primary" @click="CloseModal">Close</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <!-- Ticket table -->
        <VCard class="mt-5" v-if = tableVisible>
            <VCardText>
                <VDivider />

                <VTable class="text-no-wrap">
                    <!-- TicketId, EventName, Username, Type, Status, Actions -->
                    <thead>
                        <tr>
                            <th>TicketId</th>
                            <th>Event Name</th>
                            <th>Username</th>
                            <th>Type</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr v-for="ticket in tickets" :key="ticket.ticketId">
                            <td>{{ ticket.ticketId }}</td>
                            <td>{{ ticket.eventname }}</td>
                            <td>{{ ticket.username }}</td>
                            <td>{{ ticket.type }}</td>
                            <td>{{ ticket.status }}</td>
                            <td>
                                <v-btn variant="outlined" @click="changeTicketStatus(ticket.ticketId)">Verify</v-btn>
                            </td>
                        </tr>
                    </tbody>
                </VTable>
            </VCardText>
        </VCard>
    </section>
</template>