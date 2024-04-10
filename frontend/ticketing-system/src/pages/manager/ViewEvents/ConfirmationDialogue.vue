

<template>
        <v-dialog v-model="cancelConfirmModalOpen" max-width="500">
            <v-card>
                <v-card-title>
                    <span class="headline">Cancellation Confirmation</span>
                </v-card-title>
                <v-card-text class="text-center">
                    <v-icon class="error-icon">mdi-alert-octagon-outline</v-icon>
                    Warning: Are you sure you want to cancel this event? This action cannot be undone!
                </v-card-text>
                <v-card-actions class="justify-center">
                    <v-btn color="error" @click.native="confirm">Confirm</v-btn>
                    <v-btn color="primary" @click.native="close">Close</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
</template>

<script>
  export default {
    name: "ConfirmationDialogue",
    data() {
      return {
        dialog: false,
        resolve: null,
        reject: null,
      };
    },

    methods: {
      open() {
        this.dialog = true;
        return new Promise((resolve, reject) => {
          this.resolve = resolve;
          this.reject = reject;
        });
      },
      confirm() {
        this.resolve(true);
        this.dialog = false;
      },
      close() {
        this.resolve(false);
        this.dialog = false;
      },
    },
  };
</script>
