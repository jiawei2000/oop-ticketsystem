<template>
    <v-container class="h-100" style="max-width: 1280px;">
        <v-row class="fill-height align-center">
            <v-col cols="12" md="6">

                <v-card class="mx-auto my-auto h-100 pa-12" elevation="8" max-width="448" rounded="lg">
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
                    </v-card>

                    <v-btn block color="blue" size="large">
                        Log In
                    </v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>