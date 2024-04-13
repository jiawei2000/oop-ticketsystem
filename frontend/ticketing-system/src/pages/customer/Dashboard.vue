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
                        <div class="mb-3">
                            <v-icon>mdi-account</v-icon>
                            User Balance
                        </div>

                        <div class="mb-10">
                            Credit Remaining: ${{ this.userBalance }}
                        </div>
                    
                        <div>
                            <v-icon>mdi-ticket</v-icon>
                            Purchased Tickets
                        </div>
                        <VTable class="text-no-wrap">
                            <thead>
                                <tr>
                                    <th>Event</th>
                                    <th>Venue</th>
                                    <th>Price</th>
                                    <th>Date</th>
                                </tr>
                            </thead>

                            <tbody>
                                <tr v-for="event in customerEvents" :key="event.eventId">
                                    <td>{{ event.eventName }}</td>
                                    <td>{{ event.venue }}</td>
                                    <td>{{ event.price }}</td>
                                    <td>{{ event.date }}</td>
                                </tr>
                            </tbody>
                        </VTable>
                        
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
                                <th>Amount Spent</th>
                                <th>Event</th>
                                <th>Status</th>
                                <th>Quantity</th>
                            </tr>
                        </thead>

                        <tbody>
                            <tr v-for="transaction in transactionHistory" :key="transaction.transaction.transactionId">
                                <td>{{ transaction.transaction.date }}</td>
                                <td>{{ transaction.amountSpent }}</td>
                                <td>{{ transaction.eventName }}</td>
                                <td>{{ transaction.transaction.status }}</td>
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
            customerEvents: [],
            userId: localStorage.getItem("UserId"),
            username: localStorage.getItem("UserName"),
            userBalance: 0
        };
    },
    methods: {
        async fetchCustomerBalance() {
            try {
                const URL = `http://localhost:8080/api/customer/details/${this.userId}`;
                const response = await axios.get(URL);
                const userDetails = response.data;
                this.userBalance = userDetails.balance;
            } catch (error) {
                console.error("Error fetching customer balance:", error);
            }
        },
        async fetchCustomerEvents() {
            try {
                const URL = `http://localhost:8080/api/customer/customerEvents/${this.userId}`;
                const response = await axios.get(URL);
                this.customerEvents = response.data;
            } catch (error) {
                console.error("Error fetching customer events:", error);
            }
        },
        async fetchTransactionHistory() {
            try {
                const URL = `http://localhost:8080/api/customer/transactionDetailsHistory/${this.userId}`;
                const response = await axios.get(URL);
                this.transactionHistory = response.data;
            } catch (error) {
                console.error("Error fetching transaction history:", error);
            }
        }
    },
    mounted() {
        this.fetchCustomerEvents();
        this.fetchTransactionHistory();
        this.fetchCustomerBalance();
    }
}
</script>