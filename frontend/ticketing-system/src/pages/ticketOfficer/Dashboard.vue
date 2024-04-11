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
                            <v-text>
                                <v-icon>mdi-calendar</v-icon>
                                Event Details
                            </v-text>
                        </div>

                        <div class="mb-3">
                            <v-text>
                                Name of Event: 
                            </v-text>
                        </div>
                        <div class="mb-3">
                            <v-text>
                                Venue:
                            </v-text>
                        </div>
                        <div class="mb-3">
                            <v-text>
                                Date:
                            </v-text>
                        </div>
                        <div class="mb-3">
                            <v-text>
                                Time:
                            </v-text>
                        </div>
                        <div class="mb-3">
                            <v-text>
                                Price:
                            </v-text>
                        </div>
                        <div class="mb-3">
                            <v-text>
                                Cancellation Fee:
                            </v-text>
                        </div>
                        <div class="mb-3">
                            <v-text>
                                Stock:
                            </v-text>
                        </div>
                    </VCardText>
                </VCard>
            </v-col>
            <v-col cols="8">
                <VCard>
                    <VCardText>
                        <div class="mb-3">
                            <v-text>
                                <v-icon>mdi-history</v-icon>
                                Transaction History
                            </v-text>
                        </div>
                    </VCardText>
                    <VTable class="text-no-wrap">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Time</th>
                                <th>Quantity</th>
                                <th></th>
                            </tr>
                        </thead>

                        <tbody>
                            <tr v-for="transaction in transactionHistory" :key="transaction.transaction.transactionId">
                                <td>{{ transaction.transaction.date }}</td>
                                <td>{{ transaction.amountSpent }}</td>
                                <td>{{ transaction.eventName }}</td>
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
            event: null,
            userId: localStorage.getItem('UserId'),
            username: localStorage.getItem('UserName'),
        };
    },
    methods: {
        
    }
}
</script>