<template>
    <v-container class="h-100" style="max-width: 1280px;">
        <v-row class="fill-height align-center">
            <v-col cols="12" md="6">

                <v-card class="mx-auto my-auto h-100 pa-12" elevation="8" max-width="448" rounded="lg">
                    <v-tabs v-model="tab">
                        <v-tab value="login">Log In</v-tab>
                        <v-tab value="register">Register</v-tab>
                    </v-tabs>
                    <v-window v-model="tab">
                        <v-window-item value="login">
                            <h3 class="mb-5 text-subtitle-3">
                                Hey there! Good to see you back 
                            </h3>
                            <div class="text-subtitle-1 text-medium-emphasis">Account</div>

                            <v-text-field density="compact" placeholder="Email address" prepend-inner-icon="mdi-email-outline"
                                variant="outlined"></v-text-field>

                            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">Password
                            </div>

                            <v-text-field :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
                                :type="visible ? 'text' : 'password'" density="compact" placeholder="Enter your password"
                                prepend-inner-icon="mdi-lock-outline" variant="outlined" @click:append-inner="visible = !visible"
                                ></v-text-field>

                            <v-btn block color="blue" size="large">
                                Log In
                            </v-btn>
                        </v-window-item>
                        <v-window-item value="register">
                            <h3 class="mb-5 text-subtitle-3">
                                Create an account today!
                            </h3>
                            <div class="text-subtitle-1 text-medium-emphasis">Account</div>

                            <v-text-field density="compact" placeholder="Email address" prepend-inner-icon="mdi-email-outline"
                                variant="outlined"></v-text-field>

                            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">New Password
                            </div>

                            <v-text-field :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
                                :type="visible ? 'text' : 'password'" density="compact" placeholder="Enter your password"
                                prepend-inner-icon="mdi-lock-outline" variant="outlined" @click:append-inner="visible = !visible"
                                ></v-text-field>

                            <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">Confirm Password
                            </div>

                            <v-text-field :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
                                :type="visible ? 'text' : 'password'" density="compact" placeholder="Confirm Password"
                                prepend-inner-icon="mdi-lock-outline" variant="outlined" @click:append-inner="visible = !visible"
                                ></v-text-field>

                            <v-btn block color="blue" size="large">
                                Register
                            </v-btn>
                        </v-window-item>
                    </v-window>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>
<script>
export default {
  data() {
    return {
      tab: 'login',
      visible: false
    };
  }
};
</script>
                    <!-- <h3 class="mb-5 text-subtitle-3">
                        Hey there! Good to see you back 
                    </h3>
                    <div class="text-subtitle-1 text-medium-emphasis">Account</div>

                    <v-text-field density="compact" placeholder="Email address" prepend-inner-icon="mdi-email-outline"
                        variant="outlined"></v-text-field>

                    <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">Password
                    </div>

                    <v-text-field :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
                        :type="visible ? 'text' : 'password'" density="compact" placeholder="Enter your password"
                        prepend-inner-icon="mdi-lock-outline" variant="outlined" @click:append-inner="visible = !visible"
                        ></v-text-field> -->
                        
                <!-- <v-btn block color="blue" size="large">
                    Log In
                </v-btn> -->