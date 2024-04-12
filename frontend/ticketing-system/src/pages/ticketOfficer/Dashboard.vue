<template>
    <section>
        <v-row>
            <v-col cols="12" class="text-center">   
                <h1>    
                    Welcome Back, {{ this.username }}!
                </h1>
            </v-col>
        </v-row>

        <v-row>
            <v-col cols="4">
                <VCard>
                    <VCardText>
                        <div class="mb-10">
                            <v-icon>mdi-calendar</v-icon>
                            Event Details
                        </div>

                        <div class="mb-3">
                            Name of Event: {{ this.event.eventName }}
                        </div>
                        <div class="mb-3">
                            Venue: {{ this.event.venue }}
                        </div>
                        <div class="mb-3">
                            Date: {{ this.event.date }}
                        </div>
                        <div class="mb-3">
                            Time: {{ this.event.time }}
                        </div>
                        <div class="mb-3">
                            Price: {{ this.event.price }}
                        </div>
                        <div class="mb-3">
                            Cancellation Fee: {{ this.event.cancellationFee }}
                        </div>
                        <div class="mb-3">
                            Stock: {{ this.event.stock }}
                        </div>
                    </VCardText>
                </VCard>
            </v-col>
            <v-col cols="8">
                <VCard>
                    <VCardText>
                        <div class="mb-3">
                            <v-icon>mdi-history</v-icon>
                            Transaction History
                        </div>
                    </VCardText>
                    <VTable class="text-no-wrap">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Mode of Purchase</th>
                                <th>Quantity</th>
                            </tr>
                        </thead>

                        <tbody>
                            <tr v-for="transaction in transactionHistory" :key="transaction.transaction.transactionId">
                                <td>{{ transaction.transaction.date }}</td>
                                <td>{{ transaction.tickets[0].type }}</td>
                                <td>{{ transaction.transaction.numTicketPurchased }}</td>
                            </tr>
                        </tbody>
                    </VTable>                                                               
                </VCard>
            </v-col>
        </v-row>
    </section>
</template>
<script>
import axios from 'axios';
export default {
    data() {
        return {
            transactionHistory: [],
            event: {},
            userId: localStorage.getItem('UserId'),
            eventId: localStorage.getItem('EventId'),
            username: localStorage.getItem('UserName'),
        };
    },
    methods: {
        async fetchEvent() {
            try {
                const URL = `http://localhost:8080/api/events/${this.userId}`;
                const response = await axios.get(URL);
                this.event = response.data;
            } catch (error) {
                console.error("Error fetching customer balance:", error);
            }
        },
        async fetchTransactions() {
            try {
                const URL = `http://localhost:8080/api/ticketofficer/getTransactionsByEventId/${this.eventId}`;
                const response = await axios.get(URL);
                this.transactionHistory = response.data;
            } catch (error) {
                console.error("Error fetching customer balance:", error);
            }
        },
    },
    mounted() {
        this.fetchEvent();
        this.fetchTransactions();
    }
}
</script>