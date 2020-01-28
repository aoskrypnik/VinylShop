<template>
  <div>
    <!--String types-->
    <span v-if="isStringType">{{value}}</span>
  </div>
</template>

<script>

import * as SchemaUtils from '@/schemas/utils'

export default {
  props: ['value', 'type'],
  computed: {
    isStringType() {
      return SchemaUtils.isStringType(this.type, this.$store.getters.getSchema)
    }
  }
}
</script>

<style>

</style>