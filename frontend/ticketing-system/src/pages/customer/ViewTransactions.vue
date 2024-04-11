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
              <th>Event Name</th>
              <th>Event ID</th>
              <th>Date</th>
              <th>Number of Tickets Purchased</th>
              <th>Amount Spent</th>
              <th>Type</th>
              <th>Status</th>
              <th>Actions</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="transaction in transactions" :key="transaction.transaction.transactionId">
              <td>{{ transaction.eventName }}</td>
              <td>{{ transaction.transaction.eventId }}</td>
              <td>{{ transaction.transaction.date }}</td>
              <td>{{ transaction.transaction.numTicketPurchased }}</td>
              <td>{{ transaction.amountSpent }}</td>
              <td>{{ transaction.tickets[0].type }}</td>
              <td>{{ transaction.transaction.status }}</td>
              <td>
                <v-btn class="mr-2" variant="outlined" @click="redirectToViewTicketPage(transaction.transaction.transactionId, transaction.transaction.eventId)">View Details</v-btn>
                <v-btn v-if="transaction.transaction.status !== 'Refunded' && transaction.transaction.status !== 'Cancelled'" variant="outlined" color="red" @click="confirmRefund(transaction.transaction.transactionId)">Refund Transaction</v-btn>
              </td>
            </tr>
          </tbody>
        </VTable>
      </VCardText>
    </VCard>

    <!-- Confirmation dialog -->
    <v-dialog v-model="showConfirmationDialog" max-width="500">
      <v-card>
        <v-card-title>Confirmation</v-card-title>
        <v-card-text>
          Are you sure you want to refund this transaction?
        </v-card-text>
        <v-card-actions>
          <v-btn @click="cancelRefund">Cancel</v-btn>
          <v-btn @click="proceedRefund">Confirm</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </section>
</template>

<script setup>
import axios from "@axios";
import { onMounted, ref } from "vue";

const searchQuery = ref("");
const transactions = ref([]);
const showConfirmationDialog = ref(false);
const customerId = localStorage.getItem("UserId"); // Replace with actual customer ID
let selectedTransactionId = null;

const fetchTransactions = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/api/customer/transactionDetailsHistory/${customerId}`);
    transactions.value = response.data.map(item => ({
      eventName: item.eventName,
      transaction: item.transaction,
      amountSpent: item.amountSpent,
      tickets: item.tickets
    }));
  } catch (error) {
    console.error('Error fetching transactions:', error);
  }
};

onMounted(() => {
  fetchTransactions();
});

const confirmRefund = (transactionId) => {
  selectedTransactionId = transactionId;
  showConfirmationDialog.value = true;
};

const cancelRefund = () => {
  showConfirmationDialog.value = false;
};

const proceedRefund = async () => {
  showConfirmationDialog.value = false;
  try {
    await axios.put(`http://localhost:8080/api/customer/cancelTransaction/${selectedTransactionId}`);
    // Refresh data after refund
    await fetchTransactions();
  } catch (error) {
    console.error("Error refunding transaction:", error.message);
    // Optional: Show error message to the user
  }
};

const redirectToViewTicketPage = (transactionId, eventId) => {
  const customerId = localStorage.getItem("UserId");
  window.location.href = `http://localhost:5173/customer/viewticket?customerId=${customerId}&eventId=${eventId}&transactionId=${transactionId}`;
};

</script>
