<script setup>
import axios from "@axios";

const ticketInfo = ref([]);
const qrCodeURL = "http://localhost:8080/api/ticket/qrcode/1";

onMounted(async () => {
  const urlParams = new URLSearchParams(window.location.search);
  const customerId = urlParams.get("customerId");
  const eventId = urlParams.get("eventId");
  const transactionId = urlParams.get("transactionId");
  
  if (customerId && eventId && transactionId) {
    await fetchTicketInfo(customerId, transactionId);
    await fetchEventInfo(eventId);
  }
});

const fetchTicketInfo = async (customerId, transactionId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/customer/purchasedTickets/${customerId}`);
    ticketInfo.value = response.data.filter(ticket => ticket.transactionId === parseInt(transactionId));
  } catch (error) {
    console.error('Error fetching ticket info:', error);
  }
};

const fetchEventInfo = async (eventId) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/events/${eventId}`);
    const eventInfo = response.data;
    ticketInfo.value.forEach(ticket => {
      ticket.eventName = eventInfo.eventName;
      ticket.eventDate = eventInfo.date;
      ticket.eventTime = eventInfo.time;
      ticket.eventLocation = eventInfo.venue;
      ticket.eventPrice = eventInfo.price;
    });
  } catch (error) {
    console.error('Error fetching event info:', error);
  }
};

const print = () => {
    window.print();
}
</script>

<template>
    <section>
      <VCard>
        <VCardText class="d-flex flex-wrap justify-space-between flex-column flex-sm-row print-row pa-10 pb-6">
          <template v-for="(ticket, index) in ticketInfo" :key="ticket.ticketId">
            <VCard :class="{ 'mb-3': index % 2 !== 0 }">
              <div class="d-flex justify-space-between flex-wrap flex-md-nowrap flex-column flex-md-row border-solid">
                <div class="ma-auto pa-5">
                  <VImg width="130" :src="qrCodeURL" />
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