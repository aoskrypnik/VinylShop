<template>
  <div v-if="type.isArray && !isEnum">
    <single-item
            v-for="singleValue in value"
            :key="singleValue"

            :value="singleValue"
            :type="type"
            :schema="schema"
            :large="large"
    ></single-item>
  </div>
  <single-item
          v-else

          :value="value"
          :type="type"
          :schema="schema"
          :large="large"
  ></single-item>
</template>

<script>

import SingleItem from './SingleItem'
import * as SchemaUtils from '@/schemas/utils'

export default {
  components: {
    SingleItem
  },
  props: ['type', 'value', 'schema', 'large'],
  computed: {
    isEnum() {
      return SchemaUtils.isEnum(this.type)
    }
  }
}
</script>

<style>

</style>