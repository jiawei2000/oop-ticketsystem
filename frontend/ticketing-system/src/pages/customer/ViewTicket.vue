<script setup>
import axios from "@axios";

const ticketInfo = reactive({
    eventName: "Tech Exhibition",
    eventDate: "2022-12-12",
    eventTime: "12:00",
    eventLocation: "Tech Park",
    eventPrice: "100",
    eventDescription: "This is a tech exhibition"
});

const qrCodeURL = "http://localhost:8080/api/ticket/qrcode/1"

const print = () => {
    window.print();
}
</script>

<template>
    <section>

        <VCard>
            <VCardText class="d-flex flex-wrap justify-space-between flex-column flex-sm-row print-row pa-10 pb-6">

                <VCard v-for="i in 5" class="mb-3">
                    <div
                        class="d-flex justify-space-between flex-wrap flex-md-nowrap flex-column flex-md-row border-solid">
                        <div class="ma-auto pa-5">
                            <VImg width="130" :src="qrCodeURL" />
                        </div>

                        <VDivider :vertical="$vuetify.display.mdAndUp" />

                        <div :style="{ 'width': '420px' }">
                            <VCardItem>
                                <VCardTitle>{{ ticketInfo.eventName }}</VCardTitle>
                            </VCardItem>

                            <VCardText>
                                <span class="font-weight-bold">Venue :</span> {{ ticketInfo.eventLocation }}
                            </VCardText>

                            <VCardText>
                                <span cslass="font-weight-bold">Timing :</span> {{ ticketInfo.eventDate }}
                                {{
                                ticketInfo.eventTime }}
                            </VCardText>

                            <VCardText class="text-subtitle-1">
                                <span>Price :</span> <span class="font-weight-bold">${{
                                ticketInfo.eventPrice
                                    }}</span>
                            </VCardText>
                        </div>
                    </div>
                </VCard>


            </VCardText>
        </VCard>

        <div class="d-flex flex-wrap justify-end d-print-none gap-4 mt-8">
            <VBtn @click="print">
                Print
            </VBtn>
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

        .print-row {
            flex-direction: row !important;
        }
    }

    .layout-content-wrapper {
        padding-inline-start: 0 !important;
    }
}
</style>