<template>
    <section>
      <VCard title="Purchase Ticket">
        <VCardText>
          <VForm ref="purchaseTicketForm" @submit.prevent>
            <VRow>
              <VCol cols="12">
                <VTextField v-model="eventId" label="Event ID" type="number" placeholder="Event ID"
                            :rules="[requiredValidator]" />
              </VCol>
  
              <VCol cols="12">
                <VTextField v-model="numTicketPurchased" label="Number of Tickets" type="number"
                            placeholder="Number of Tickets" :rules="[requiredValidator]" />
              </VCol>
  
              <VCol cols="12">
                <VTextField v-model="userId" label="User ID" type="number" placeholder="User ID"
                            :rules="[requiredValidator]" />
              </VCol>
  
              <VCol cols="12">
                <VTextField v-model="status" label="Status" placeholder="Status" :rules="[requiredValidator]" />
              </VCol>
  
              <VCol cols="12">
                <AppDateTimePicker v-model="date" label="Date" placeholder="Date"
                                    :config="{ altInput: true, altFormat: 'j-F-Y', dateFormat: 'Y-m-d' }"
                                    :rules="[requiredValidator]" />
              </VCol>
  
              <VCol cols="12" class="d-flex gap-4">
                <VBtn type="submit" @click="purchaseTicket()">
                  <span v-if="!isLoading">Purchase Ticket</span>
                  <VProgressCircular v-if="isLoading" indeterminate color="success" :size="25" />
                </VBtn>
  
                <VBtn type="reset" color="secondary" variant="tonal" @click="resetForm">
                  Reset
                </VBtn>
              </VCol>
            </VRow>
          </VForm>
        </VCardText>
      </VCard>
    </section>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { requiredValidator } from '@validators';
  import axios from "@axios";
  
  const purchaseTicketForm = ref(null);
  const eventId = ref('');
  const numTicketPurchased = ref('');
  const userId = ref('');
  const status = ref('Active');
  const date = ref('');
  const isLoading = ref(false);
  
  const purchaseTicket = async () => {
    isLoading.value = true;
    const valid = await purchaseTicketForm.value.validate()
    if (!valid.valid) {
      isLoading.value = false;
      return;
    }
  
    const purchaseTicketURL = "http://localhost:8080/api/ticketofficer/purchaseTicket";
  
    const data = {
      eventId: eventId.value,
      numTicketPurchased: numTicketPurchased.value,
      userId: userId.value,
      status: status.value,
      date: date.value,
    };
  
    console.log("Purchasing ticket...");
    axios.post(purchaseTicketURL, data)
      .then(response => {
        console.log(response.data);
      })
      .catch(error => {
        console.log(error.message);
      });
    isLoading.value = false;
  }

  const resetForm = () => {
  eventId.value = '';
  numTicketPurchased.value = '';
  userId.value = '';
  status.value = 'Active';
  date.value = ''; // Resetting the date field
}
  </script>
  