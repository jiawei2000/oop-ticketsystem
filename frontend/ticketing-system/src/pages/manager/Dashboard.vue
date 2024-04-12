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
                            Upcoming Event Details
                        </div>

                        <div class="mb-3">
                            Name of Event: {{ this.upcomingEvent.eventName }}
                        </div>
                        <div class="mb-3">
                            Venue: {{ this.upcomingEvent.venue }}
                        </div>
                        <div class="mb-3">
                            Date: {{ this.upcomingEvent.date }}
                        </div>
                        <div class="mb-3">
                            Time: {{ this.upcomingEvent.time }}
                        </div>
                        <div class="mb-3">
                            Price: {{ this.upcomingEvent.price }}
                        </div>
                        <div class="mb-3">
                            Cancellation Fee: {{ this.upcomingEvent.cancellationFee }}
                        </div>
                        <div class="mb-3">
                            Stock: {{ this.upcomingEvent.stock }}
                        </div>
                    </VCardText>
                </VCard>
            </v-col>
            <v-col cols="8">
                <VCard>
                    <VCardText>
                        <div class="mb-3">
                            <v-icon>mdi-calendar</v-icon>
                            Existing Events
                        </div>
                    </VCardText>
                    <VTable class="text-no-wrap">
                        <thead>
                            <tr>
                                <th>Event</th>
                                <th>Date</th>
                                <th>Time</th>
                                <th>Venue</th>
                                <th>Status</th>
                                <th>Price</th>
                                <th>Stock</th>
                            </tr>
                        </thead>

                        <tbody>
                            <tr v-for="event in events" :key="event.eventId">
                                <td>{{ event.eventName }}</td>
                                <td>{{ event.date }}</td>
                                <td>{{ event.time }}</td>
                                <td>{{ event.venue }}</td>
                                <td>{{ event.status }}</td>
                                <td>{{ event.price }}</td>
                                <td>{{ event.stock }}</td>
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
            events: [],
            userId: localStorage.getItem('UserId'),
            username: localStorage.getItem('UserName'),
            upcomingEvent: {}
        };
    },
    methods: {
        async getEvents() {
            try {
                const response = await axios.get(`http://localhost:8080/api/events`);
                this.events = response.data;
                this.getUpcomingEvent();
            } catch (error) {
                console.error(error);
            }
        },
        getUpcomingEvent() {
            const currentDate = new Date();
            const upcomingEvents = this.events.filter(event => new Date(event.date) >= currentDate);
            upcomingEvents.sort((a, b) => new Date(a.date) - new Date(b.date));
            this.upcomingEvent = upcomingEvents.length > 0 ? upcomingEvents[0] : null;
        }
    },
    mounted() {
        this.getEvents();
    }
}
</script>