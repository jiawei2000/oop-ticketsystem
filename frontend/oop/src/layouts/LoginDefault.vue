<template>

      <AppBar />
  
      <default-view/>
  
      <AppFooter />
</template>
  
  <script>
  import DefaultView from './View.vue'
  import AppBar from '@/components/AppBar.vue'
  import AppFooter from '@/components/AppFooter.vue'
  
  export default {
    name: 'Home',
    components: {
      AppBar,
      AppFooter,
      DefaultView
    }
  }
  </script>
  