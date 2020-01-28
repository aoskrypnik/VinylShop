<template>
  <div>
    <form-view :schema="$route.params.schema" :item="$route.params.item"></form-view>
  </div>
</template>

<script>

import FormView from '@/components/views/FormView'

export default {
  components: {
    FormView
  },
  computed: {
  }
}
</script>

<style lang="scss" scoped>

</style>
