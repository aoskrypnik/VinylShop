<template>
  <div>
    <entry-view :schema="$route.params.schema" :item="$route.params.item"></entry-view>
  </div>
</template>

<script>

  import EntryView from '@/components/views/EntryView'

  export default {
    components: {
      EntryView
    },
    computed: {
    }
  }
</script>

<style lang="scss" scoped>

</style>
