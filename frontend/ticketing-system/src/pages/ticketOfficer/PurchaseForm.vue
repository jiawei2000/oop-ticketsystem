<template>
    <section>
        <VCard title="Purchase Ticket">
            <VCardText>
                <VForm ref="refForm" @submit.prevent>
                    <VRow>
                        <VCol cols="12">
                            <VTextField v-model="eventId" label="Event ID" type="text" :rules="[requiredValidator]" />
                        </VCol>

                        <VCol cols="12">
                            <VTextField v-model="numberOfTickets" label="Number of Tickets" type="number" :rules="[requiredValidator]" />
                        </VCol>

                        <VCol cols="12">
                            <AppDateTimePicker v-model="date" label="Date" placeholder="Date"
                                :config="{ altInput: true, altFormat: 'j-F-Y', dateFormat: 'Y-m-d' }"
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

<!-- <script>
export default {
    data() {
        return {
            eventId: '',
            numberOfTickets: '',
            date: ''
        };
    },
    methods: {
        requiredValidator(value) {
            return !!value || 'Required.';
        },
        purchaseTicket() {
            // Implement the logic to handle ticket purchase
            console.log('Event ID:', this.eventId);
            console.log('Number of Tickets:', this.numberOfTickets);
            console.log('Date:', this.date);
        }
    }
};
</script> -->
