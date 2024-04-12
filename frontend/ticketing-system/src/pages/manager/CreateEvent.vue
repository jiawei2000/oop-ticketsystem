<script setup>
import { requiredValidator } from '@validators';
import axios from "@axios";
import { useRouter } from "vue-router";

const refForm = ref();
const eventName = ref('');
const venue = ref('');
const date = ref('');
const time = ref('');
const price = ref('');
const cancellationFee = ref('');
const stock = ref('');
const isLoading = ref(false);
const router = useRouter();

const createEvent = async () => {
    isLoading.value = true;
    const valid = await refForm.value.validate()
    if (valid.valid == false) {
        isLoading.value = false;
        return;
    }

    const createEventURL = "events";

    let data = {
        eventName: eventName.value,
        venue: venue.value,
        date: date.value,
        time: time.value,
        price: price.value,
        stock: stock.value,
        cancellationFee: cancellationFee.value,
        managerId: localStorage.getItem("UserId"),
    }

    console.log("Creating event...");
    axios.post(createEventURL, data)
        .then(response => {
            console.log(response.data);
            //Redirect to events list page
            router.push({ name: "manager-ViewEvents" });
        })
        .catch(error => {
            console.log(error.message);
        });
    isLoading.value = false;
}

</script>

<template>
    <section>
        <VCard title="Create Event">
            <VCardText>
                <VForm ref="refForm" @submit.prevent>
                    <VRow>
                        <VCol cols="12">
                            <VTextField v-model="eventName" label="Event Name" :rules="[requiredValidator]" />
                        </VCol>

                        <VCol cols="12">
                            <VTextField v-model="venue" label="Venue" placeholder="Venue"
                                :rules="[requiredValidator]" />
                        </VCol>

                        <VCol cols="12">
                            <AppDateTimePicker v-model="date" label="Date" placeholder="Date"
                                :config="{ altInput: true, altFormat: 'j-F-Y', dateFormat: 'Y-m-d' }"
                                :rules="[requiredValidator]" />
                        </VCol>

                        <VCol cols="12">
                            <AppDateTimePicker v-model="time" label="Time"
                                :config="{ enableTime: true, noCalendar: true, dateFormat: 'H:i' }"
                                :rules="[requiredValidator]" />
                        </VCol>

                        <VCol cols="6">
                            <VTextField v-model="price" label="Price" type="number" placeholder="Price"
                                :rules="[requiredValidator]" />
                        </VCol>

                        <VCol cols="6">
                            <VTextField v-model="cancellationFee" label="Cancellation Fee" type="number"
                                placeholder="Cancellation Fee" :rules="[requiredValidator]" />
                        </VCol>

                        <VCol cols="6">
                            <VTextField v-model="stock" label="Stock" type="number" placeholder="Stock"
                                :rules="[requiredValidator]" />
                        </VCol>

                        <VCol cols="12" class="d-flex gap-4">
                            <VBtn type="submit" @click="createEvent()">
                                <span v-if="!isLoading">Create</span>
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