<template>
    <section>
        <v-row>
            <v-col cols="12" class="text-center">   
                <h1>    
                    Welcome Back, user!
                </h1>
            </v-col>
        </v-row>

        <v-row>
            <v-col cols="4">
                <VCard>
                    <VCardText>
                        <div class="mb-3">
                            <v-text>
                                <v-icon>mdi-account</v-icon>
                                Balance
                            </v-text>
                        </div>

                        <div class="mb-10">
                            <v-text>
                                Credit Remaining:
                            </v-text>
                        </div>

                        <div>
                            <v-text>
                                <v-icon>mdi-history</v-icon>
                               Transaction History 
                            </v-text>
                        </div>
                        <VTable class="text-no-wrap">
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Amount Spent</th>
                                    <th>Event</th>
                                    <th>Quantity</th>
                                </tr>
                            </thead>

                            <tbody>
                                <tr v-for="event in events" :key="event.eventId">
                                    <td>{{ event.eventName }}</td>
                                    <td>{{ event.venue }}</td>
                                    <td>{{ formatDate(event.date) }}</td>
                                    <td>{{ event.time }}</td>
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
                            <v-text>
                                <v-icon>mdi-ticket</v-icon>
                                Purchased Tickets
                            </v-text>
                        </div>
                    </VCardText>
                    <VTable class="text-no-wrap">
                        <thead>
                            <tr>
                                <th>Event</th>
                                <th>Quantity</th>
                                <th>Venue</th>
                                <th>Date</th>
                            </tr>
                        </thead>

                        <tbody>
                            <tr v-for="event in events" :key="event.eventId">
                                <td>{{ event.eventName }}</td>
                                <td>{{ event.venue }}</td>
                                <td>{{ formatDate(event.date) }}</td>
                                <td>{{ event.time }}</td>
                            </tr>
                        </tbody>
                    </VTable>                                                               
                </VCard>
            </v-col>
        </v-row>
    </section>
</template>