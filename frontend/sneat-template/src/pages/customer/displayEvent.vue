<script setup>

import DisplayEventTable from '@/views/pages/tables/DisplayEventTable.vue';
</script>

<template>
  <VRow>
    <VCol cols="12">
      <VCard title="Event List">
        <Suspense> <DisplayEventTable /></Suspense>
      </VCard>
    </VCol>
  </VRow>
</template>
