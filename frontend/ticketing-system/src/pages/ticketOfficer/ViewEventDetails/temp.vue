<template>
    <v-card class="my-6 mx-6">
        <v-card-title>Event Details of EventId: {{ event.eventId }}</v-card-title>
        <v-card-text>
            <!-- form (EventId, Eventname, status,  venue, date, time, price, cancellation fee, stock,  ) -->

            <v-form>
                <v-container>
                    <!-- EventName and status -->
                    <v-row>
                        <v-col cols=8>
                            <v-text-field v-model="event.eventname" label="Event Name" readonly></v-text-field>
                        </v-col>
                        <v-col cols=4>
                            <v-text-field v-model="event.status" label="Status" readonly></v-text-field>
                        </v-col>
                    </v-row>
                    <!-- Date and time -->
                    <v-row>
                        <v-col cols=6>
                            <v-text-field v-model="event.date" label="Date" readonly></v-text-field>
                        </v-col>
                        <v-col cols=6>
                            <v-text-field v-model="event.time" label="Time" readonly></v-text-field>
                        </v-col>
                    </v-row>
                    <!-- Price, Cancellation Fee, Stock -->
                    <v-row>
                        <v-col cols=4>
                            <v-text-field v-model="event.price" label="Price($)" readonly></v-text-field>
                        </v-col>
                        <v-col cols=4>
                            <v-text-field v-model="event.cancellationFee" label="Cancellation Fee($)"
                                readonly></v-text-field>
                        </v-col>
                        <v-col cols=4>
                            <v-text-field v-model="event.stock" label="Stock" readonly></v-text-field>
                        </v-col>
                    </v-row>
                </v-container>
            </v-form>
        </v-card-text>
        <v-card-actions class="justify-end">
            <v-btn variant="outlined" secondary="error" @click=editEventDetails>Edit Details</v-btn>
            <v-btn variant="outlined" color="error" @click=cancelEvent>Cancel</v-btn>
        </v-card-actions>
    </v-card>

    <!-- Event statistics -->
    <v-card class="my-6 mx-6">
        <v-card-title>Event Statistics</v-card-title>
        <v-card-text>
            <!-- form (EventId, Eventname, status,  venue, date, time, price, cancellation fee, stock,  ) -->
            <v-form>
                <v-container>
                    <!-- Total sales -->
                    <v-row>
                        <v-col>
                            <v-text-field v-model="eventStatistics.totalRevenue" label="Total Revenue"
                                readonly></v-text-field>
                        </v-col>
                    </v-row>
                    <!-- sales rev, refund rev, sales -->
                    <v-row>
                        <v-col cols=5>
                            <v-text-field v-model="eventStatistics.saleRevenue" label="Sales Revenue"
                                readonly></v-text-field>
                        </v-col>
                        <v-col cols=5>
                            <v-text-field v-model="eventStatistics.refundRevenue" label="Refund Revenue"
                                readonly></v-text-field>
                        </v-col>
                        <v-col cols=2>
                            <v-text-field v-model="eventStatistics.sales" label="Sales" readonly></v-text-field>
                        </v-col>
                    </v-row>
                    <!-- Price, Cancellation Fee, Stock -->
                    <v-row>
                        <v-col cols=4>
                            <v-text-field v-model="eventStatistics.attendance" label="Attendance"
                                readonly></v-text-field>
                        </v-col>
                        <v-col cols=4>
                            <v-text-field v-model="eventStatistics.noRefunds" label="Number of refunds"
                                readonly></v-text-field>
                        </v-col>
                        <v-col cols=4>
                            <v-text-field v-model="eventStatistics.noCancelled" label="Number of tickets to cancel"
                                readonly></v-text-field>
                        </v-col>
                    </v-row>
                </v-container>
            </v-form>
        </v-card-text>
    </v-card>
</template>