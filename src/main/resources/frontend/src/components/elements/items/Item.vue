<template>
  <div v-if="type.isArray">
    <single-item v-for="singleValue in value" :value="singleValue" :type="type.type" :key="singleValue"></single-item>
  </div>
  <single-item  v-else :value="value" :type="type"></single-item>
</template>

<script>

import SingleItem from './SingleItem'

export default {
  components: {
    SingleItem
  },
  props: ['type', 'value']
}
</script>

<style>

</style>