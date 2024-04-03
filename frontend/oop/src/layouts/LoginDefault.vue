<template>
    <v-app>
        <v-card>
            <v-card-title>
                <h1>Login</h1>
            </v-card-title>
            <v-card-text>
                <v-form>
                    <v-text-field
                        v-model="email"
                        label="Email"
                        required
                    ></v-text-field>
                    <v-text-field
                        v-model="password"
                        label="Password"
                        required
                        type="password"
                    ></v-text-field>
                    <v-btn
                        @click="login"
                        color="primary"
                    >
                        Login
                    </v-btn>
                </v-form>
            </v-card-text>
        </v-card>

      <!-- <AppBar />
  
      <default-view/>
  
      <AppFooter /> -->
    </v-app>
  </template>
  
  <script>
//   import DefaultView from './View.vue'
//   import AppBar from '@/components/AppBar.vue'
//   import AppFooter from '@/components/AppFooter.vue'
  
//   export default {
//     name: 'Home',
//     components: {
//       AppBar,
//       AppFooter,
//       DefaultView
//     }
//   }
  </script>
  