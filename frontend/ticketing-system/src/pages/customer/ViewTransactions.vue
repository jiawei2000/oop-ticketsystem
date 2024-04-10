<script setup>
import axios from "@axios";
import { onMounted } from "vue";
import { ref } from 'vue';

const searchQuery = ref("");
const transactions = ref([]);

const fetchTransactions = async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/customer/transactionHistory/1');
        transactions.value = response.data;
    } catch (error) {
        console.error('Error fetching transactions:', error);
    }
};

onMounted(() => {
    fetchTransactions();
});

</script>

<template>
    <section>
        <!-- Transaction table -->
        <VCard>
            <VCardText>
                <div class="mb-3">
                    <VTextField v-model="searchQuery" placeholder="Search Transaction" />
                </div>
                <VDivider />

                <VTable class="text-no-wrap">
                    <thead>
                        <tr>
                            <th>Transaction ID</th>
                            <th>Event ID</th>
                            <th>User ID</th>
                            <th>Number of Tickets Purchased</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>

                    <tbody>
                        <tr v-for="transaction in transactions" :key="transaction.transactionId">
                            <td>{{ transaction.transactionId }}</td>
                            <td>{{ transaction.eventId }}</td>
                            <td>{{ transaction.userId }}</td>
                            <td>{{ transaction.numTicketPurchased }}</td>
                            <td>{{ transaction.status }}</td>
                            <td>
                                <v-btn class="mr-2" variant="outlined" @click="viewDetails(transaction.eventId)">View Details</v-btn>
                                <v-btn variant="outlined" color="red">Cancel Ticket</v-btn> <!-- Dummy button for now -->
                            </td>
                        </tr>
                    </tbody>
                </VTable>
            </VCardText>
        </VCard>
    </section>
</template>
