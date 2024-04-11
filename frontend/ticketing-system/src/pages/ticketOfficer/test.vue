<script setup>
import axios from "@axios";
import { ref } from 'vue';
import { useRouter } from "vue-router";

const router = useRouter();
const events = ref([]);

const getManagerEvents = async () => {
    const managerId = 1;
    const displayManagerEventsURL = "manager/getEvents/" + managerId;
    axios.get(displayManagerEventsURL)
        .then(response => {
            const data = response.data;
            events.value = data;
        })
        .catch(error => {
            console.log(error.message);
        });
}

onMounted(() => {
    getManagerEvents();
});



const sortedEvents = computed(() => {
    const ret = [];
    const allEvents = ref(events.value.length);
    for (let i = 0; i < allEvents.value; i += 3) {
        console.log("i: " , i);
        const slicedArray = events.value.splice(0, 3);
        console.log("after splice:",events.value.length);
        ret.push(slicedArray);
    }
    console.log("test ret");
    console.log(ret);
    return ret;
})

const moreDetails = (event) => {
    // console.log(event)
    router.push({ path: './ViewEventDetails/' + event.eventId  })
}

</script>


<template>
    <section>
        <v-card>
            <v-card-title><h1><b>Your Events</b></h1></v-card-title>
            <v-container>
                <v-row v-for="events in sortedEvents">
                    <v-col col=4 v-for="event in events">
                        <v-card class="mx-auto" max-width="344" variant="outlined">
                            <v-img max-height="175"
                                src="https://cdn-icons-png.freepik.com/512/4367/4367756.png"></v-img>
                            <v-card-title>{{ event.eventName }}</v-card-title>
                            <v-card-subtitle>EventId: {{ event.eventId }}</v-card-subtitle>
                            <v-card-subtitle>Status: {{ event.status }}</v-card-subtitle>
                            <v-card-actions class="justify-center">
                                <v-btn @click = "moreDetails(event)">More Details</v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-col>
                </v-row>
            </v-container>
        </v-card>
    </section>
</template>