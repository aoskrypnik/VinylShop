<template>
  <div>
    <list-view :schema="$route.params.schema" @itemSelection="goToEntry"></list-view>
  </div>
</template>

<script>

import ListView from '@/components/views/ListView'

export default {
  components: {
    ListView
  },
  computed: {
  },
  methods: {
    goToEntry(key) {
      this.$router.push({
        name: 'entry',
        params: {
          item: key,
          schema: this.$route.params.schema
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>

</style>
