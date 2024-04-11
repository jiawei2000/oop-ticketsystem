<script setup>
import axios from "@axios";
import { onMounted, ref } from "vue";

const ticketInfo = ref([]);

const fetchTicketInfo = async (customerId, eventId, transactionId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/customer/purchasedTickets/${customerId}`);
    ticketInfo.value = response.data.filter(ticket => ticket.transactionId === parseInt(transactionId));
    await fetchEventInfo(ticketInfo.value);
    await fetchQRCodeURLs(ticketInfo.value);
  } catch (error) {
    console.error('Error fetching ticket info:', error);
  }
};

const fetchEventInfo = async (tickets) => {
  try {
    for (const ticket of tickets) {
      const response = await axios.get(`http://localhost:8080/api/events/${ticket.eventId}`);
      const eventInfo = response.data;
      ticket.eventName = eventInfo.eventName;
      ticket.eventLocation = eventInfo.venue;
      ticket.eventDate = eventInfo.date;
      ticket.eventTime = eventInfo.time;
      ticket.eventPrice = eventInfo.price;
    }
  } catch (error) {
    console.error('Error fetching event info:', error);
  }
};

const fetchQRCodeURLs = async (tickets) => {
  try {
    for (const ticket of tickets) {
      const response = await axios.get(`http://localhost:8080/api/ticket/qrcode/${ticket.ticketId}`, { responseType: 'arraybuffer' });
      const blob = new Blob([response.data], { type: 'image/png' });
      ticket.qrCode = URL.createObjectURL(blob);
    }
  } catch (error) {
    console.error('Error fetching QR code URLs:', error);
  }
};

const print = () => {
    window.print();
};

const goBack = () => {
  window.location.href = 'http://localhost:5173/customer/viewtransactions';
};

onMounted(() => {
  const urlParams = new URLSearchParams(window.location.search);
  const customerId = urlParams.get("customerId");
  const eventId = urlParams.get("eventId");
  const transactionId = urlParams.get("transactionId");
  
  if (customerId && eventId && transactionId) {
    fetchTicketInfo(customerId, eventId, transactionId);
  }
});

</script>


<template>
  <section>
    <div class="d-flex justify-between mb-4">
      <!-- Back button -->
      <div>
        <VBtn @click="goBack" class="mr-4">Back</VBtn>
      </div>
    </div>
    <VCard>
      <VCardText class="d-flex flex-wrap justify-space-between flex-column flex-sm-row print-row pa-10 pb-6">
        <template v-for="(ticket, index) in ticketInfo" :key="ticket.ticketId">
          <VCard :class="{ 'mb-3': index % 2 !== 0 }">
            <div class="d-flex justify-space-between flex-wrap flex-md-nowrap flex-column flex-md-row border-solid">
              <div class="ma-auto pa-5">
                <img v-if="ticket.qrCode" :src="ticket.qrCode" alt="QR Code" :style="{ width: '150px', height: '150px' }">
              </div>
              <VDivider :vertical="$vuetify.display.mdAndUp" />
              <div :style="{ 'width': '420px' }">
                <VCardItem>
                  <VCardTitle>{{ ticket.eventName }}</VCardTitle>
                </VCardItem>
                <VCardText>
                  <span class="font-weight-bold">Ticket ID :</span> {{ ticket.ticketId }}
                </VCardText>
                <VCardText>
                  <span class="font-weight-bold">Venue :</span> {{ ticket.eventLocation }}
                </VCardText>
                <VCardText>
                  <span class="font-weight-bold">Timing :</span> {{ ticket.eventDate }} {{ ticket.eventTime }}
                </VCardText>
                <VCardText>
                  <span class="font-weight-bold">Status :</span> {{ ticket.status }}
                </VCardText>
                <VCardText class="text-subtitle-1">
                  <span>Price :</span> <span class="font-weight-bold">${{ ticket.eventPrice }}</span>
                </VCardText>
              </div>
            </div>
          </VCard>
          <template v-if="index % 2 !== 0">
            <VDivider v-if="index !== ticketInfo.length - 1" class="mb-3" />
          </template>
        </template>
      </VCardText>
    </VCard>

    <div class="d-flex flex-wrap justify-end d-print-none gap-4 mt-8">
      <VBtn @click="print">Print</VBtn>
    </div>
  </section>
</template>
  
  <style>
  @media print {
    .v-application {
      background: none !important;
    }
  
    .v-navigation-drawer,
    .layout-vertical-nav,
    .app-customizer-toggler,
    .layout-footer,
    .layout-navbar,
    .layout-navbar-and-nav-container {
      display: none;
    }
  
    .v-card {
      box-shadow: none !important;
    }
  
    .print-row {
      flex-direction: row !important;
    }
  
    .layout-content-wrapper {
      padding-inline-start: 0 !important;
    }
  }
  </style>  