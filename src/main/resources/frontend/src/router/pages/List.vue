<template>
  <div>
    <list-view :schema="$route.params.schema"></list-view>
  </div>
</template>

<script>

import ListView from '@/components/views/ListView'

export default {
  components: {
    ListView
  },
  computed: {
  }
}
</script>

<style lang="scss" scoped>

</style>
