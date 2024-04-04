<template>
    <v-card>
      <!-- Header and symbols -->
      <v-toolbar color="primary">
  
        <v-toolbar-title>
          <v-btn>
            Ticketing System
          </v-btn>
          
  
        </v-toolbar-title>
        

        <v-text-field
          w-25
          variant="solo"
          label="Event Search"
          append-inner-icon="mdi-magnify"
          single-line
          hide-details
          class="search-field"
        ></v-text-field>
        <v-btn>
            <!-- <v-icon :icon="`mdiSvg:${mdiAccount}`"></v-icon> -->
            Login/Register
        </v-btn>
  
      </v-toolbar>
    </v-card>
  </template>

<script>
</script>

<style scoped>
</style>